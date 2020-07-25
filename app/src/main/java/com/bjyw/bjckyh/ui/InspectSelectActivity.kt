package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.get
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Message
import com.bjyw.bjckyh.bean.daobean.Inspect
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.bjyw.bjckyh.bean.environPic
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.HttpModel
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.*
import com.bjyw.bjckyh.view.EnvironUsualView
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.bean.ZxingConfig
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.activity_inspect_select.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InspectSelectActivity : BaseActivity(), HttpModel.HttpClientListener {
    override fun onSuccess(obj: Any) {}
    private val REQUEST_CODE_SCAN=0x01
    private val REQUEST_CODE_ORDE=0x02
    private val REQUEST__CODE_IMAGES=0x03
    var isOk=true
    var siteId=0
    var coitionId=""
    var useStutusId=""
    var orderId=""
    var picList=ArrayList<environPic>()
    var environList=ArrayList<EnvironUsualView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_select)
        EventBus.getDefault().register(this)
        initView()
        initClick()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    private fun getData() {
        showDialog()
        HttpManager.getCoition(1).request(this@InspectSelectActivity){ _,data->
            data.let {data ->
                for (i in 0 until data!!.size) {
                    var environUsualView=EnvironUsualView(applicationContext)
                    environUsualView.init(this@InspectSelectActivity,i,data[i].index,isOk)
                    environUsualView.setTitle(data[i].context)
                    layout_EnvironUsualView.addView(environUsualView)
                    environList.add(environUsualView)
                    var pic=environPic(null,null)
                    picList.add(pic)
                    dismissDialog()
                }
            }
        }
    }


    private fun initClick() {
        bt_sitehistory.onClick {
            if (siteId==0){
                toast("请先扫码选择台站")
                return@onClick
            }
            var intent=Intent(this@InspectSelectActivity,SiteDeatilActivity::class.java)
            intent.putExtra("siteId",""+siteId)
            startActivity(intent)
        }
        activity_include_btback.onClick {
            this@InspectSelectActivity.finish()
        }
        activity_include_tvrignt.onClick {
            startActivityForResult(Intent(this@InspectSelectActivity,OrderListActivity::class.java),REQUEST_CODE_ORDE)
        }
        layout_saoma.onClick {
            val intent = Intent(this@InspectSelectActivity, CaptureActivity::class.java)
            var config =  ZxingConfig()
            config.isShowbottomLayout=false
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }
        bt_next.onClick {
            if (siteId !=0){
                saveInspect()
            }else{
                toast("请先扫码获取站点数据")
            }
        }
    }

    private fun saveInspect() {
        var inspect=Inspect()
        if (SPUtils.instance().getInt("userId")==-1){
            toast("请先登录")
            return
        }
        if (coitionId.equals("")){
            toast("请选择巡检条件")
            return
        }else{
            inspect.name=name
            var simpleDateFormat=SimpleDateFormat("yyyy-MM-dd")
            inspect.time=simpleDateFormat.format(Date())
            inspect.status="1"
            inspect.conId=coitionId
            if (coitionId.equals("3")){
                inspect.is_unusual="0"
            }else{
                inspect.is_unusual="1"
            }
            val unusual_dian=environList[0].getEnvironBean().is_unusual
            val unusual_xin=environList[1].getEnvironBean().is_unusual
            val unusual_room=environList[2].getEnvironBean().is_unusual
            if (!useStutusId.equals("0")){
                inspect.environmentStatus="0"
            }else{
                if (unusual_room.equals("1")){
                    inspect.environmentStatus="0"
                }else{
                    if (unusual_dian.equals("1")&&unusual_xin.equals("1")){
                        inspect.environmentStatus="0"
                    }else if(unusual_dian.equals("1")||unusual_xin.equals("1")){
                        inspect.environmentStatus="1"
                    }else{
                        inspect.environmentStatus="2"
                    }
                }
            }
        }
        if (orderId.equals("")){
            orderId=Date().time.toString()
            inspect.orderIndex=orderId
            inspect.today=true
            inspect.siteId=siteId
        }else{
            inspect.orderIndex=orderId
            inspect.today=false
            inspect.siteId=siteId
        }
        if (useStutusId.equals("")){
            toast("请选择使用状态")
            return
        }else{
            inspect.useStatus=useStutusId
        }
        showDialog()
        DbController.getInstance(applicationContext).insertOrReplaceInspect(inspect)
        saveEnvironment()
    }
    private fun saveEnvironment() {
        for (i in 0 until environList.size){
            var environ=environList[i].getEnvironBean()
            environ.onrderIndex=orderId
            if (picList[i].pic1!=null|| picList[i].pic2!=null){
                var list=ArrayList<File>()
                if (picList[i].pic1!=null){
                    list.add(convertBitmapToFile(applicationContext,picList[i].pic1!!, "pic$i"))
                }
                if (picList[i].pic2!=null){
                    list.add(convertBitmapToFile(applicationContext,picList[i].pic2!!, "pic2$i"))
                }
                updataPic(list,environ)
            }else{
                DbController.getInstance(applicationContext).insertOrReplaceEnvironment(environ)
            }
        }
        onNext()
    }

    private fun onNext() {
        dismissDialog()
        var intent=Intent(this@InspectSelectActivity,InspectMainActivity::class.java)
        intent.putExtra("siteId",siteId)
        intent.putExtra("orderId",orderId)
        intent.putExtra("isOk",isOk)
        startActivity(intent)
    }

    private fun updataPic(pic: ArrayList<File>, environ: InspectEnvironMent) {
        //拍照
        var httpModel= HttpModel(this@InspectSelectActivity)
        httpModel.postFile2(applicationContext,pic,environ)
    }
    override fun onError() {
        dismissDialog()
        errorToast("上传失败")
    }
    override fun onSuccess2(response: String, environ: InspectEnvironMent) {
        dismissDialog()
        var jsonObject= JSONObject(response)
        var pic=jsonObject.get("data").toString()
        environ.picture=pic
        DbController.getInstance(applicationContext).insertOrReplaceEnvironment(environ)
    }

    private fun initView() {
        size.visibility=View.VISIBLE
        size.text=intent.getStringExtra("orderSize")
        activity_include_tvtitle.text="台站巡检"
        activity_include_tvrignt.text="工单列表"
        activity_include_tvrignt.textColor=Color.WHITE
        val formatter = SimpleDateFormat("yyyy年MM月dd日")
        val curDate = Date(System.currentTimeMillis())
        tv_inspecttime.text=formatter.format(curDate)
        var radioButton1=RadioButton(applicationContext)
        radioButton1.text="机柜丢失"
        radioButton1.textSize=14f
        radioButton1.onClick {
            isOk = false
            environList.forEach {
                it.setOk(isOk)
            }
            coitionId="1"
        }
        radioButton1.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        var radioButton2=RadioButton(applicationContext)
        radioButton2.text="无法进入"
        radioButton2.textSize=14f
        radioButton2.onClick {
            isOk = false
            environList.forEach {
                it.setOk(isOk)
            }
            coitionId="2"
        }
        radioButton2.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        var radioButton3=RadioButton(applicationContext)
        radioButton3.text="正常巡检"
        radioButton3.textSize=14f
        radioButton3.onClick {
            isOk = true
            environList.forEach {
                it.setOk(isOk)
            }
            coitionId="3"
        }
        radioButton3.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        group_tiaojian.addView(radioButton1)
        group_tiaojian.addView(radioButton2)
        group_tiaojian.addView(radioButton3)

        var radioButton4=RadioButton(applicationContext)
        radioButton4.text="正常"
        radioButton4.textSize=14f
        radioButton4.onClick {
            useStutusId="0"

        }
        radioButton4.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        var radioButton5=RadioButton(applicationContext)
        radioButton5.text="搬迁封存"
        radioButton5.textSize=14f
        radioButton5.onClick {
            useStutusId="8"

        }
        radioButton5.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        var radioButton6=RadioButton(applicationContext)
        radioButton6.text="本地封存"
        radioButton6.textSize=14f
        radioButton6.onClick {
            useStutusId="9"

        }
        radioButton6.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        var radioButton7=RadioButton(applicationContext)
        radioButton7.text="航空管制"
        radioButton7.textSize=14f
        radioButton7.onClick {
            useStutusId="11"

        }
        radioButton7.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        group_status.addView(radioButton4)
        group_status.addView(radioButton5)
        group_status.addView(radioButton6)
        group_status.addView(radioButton7)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val content = data.getStringExtra(Constant.CODED_CONTENT)
                getQcode(content!!)
            }
        }else if (requestCode == REQUEST_CODE_ORDE && resultCode == Activity.RESULT_OK){
            if (data != null) {
                orderId= data.getStringExtra("orderId")
            }
        } else if(requestCode == REQUEST__CODE_IMAGES && resultCode == Activity.RESULT_OK){
            try {
                var fileName= map["name"] as String
                //查询的条件语句
                var selection = MediaStore.Images.Media.DISPLAY_NAME + "=? "
                var cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,arrayOf(MediaStore.Images.Media._ID),selection, arrayOf(fileName),null)
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        var uri =  ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(0))
                        showPic(uri)
                    }while (cursor.moveToNext())
                }
            } catch (e:Exception ) {
                e.printStackTrace()
            }
        }
    }
    private fun showPic(uri: Uri) {
        val bitmap:Bitmap = TakePhoto.getBitmapFormUri(applicationContext, uri)
        if (map.get("type")==0){
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment1).scaleType=ImageView.ScaleType.CENTER_CROP}
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment1).setImageBitmap(bitmap)}
            picList[map.get("position") as Int].pic1=bitmap
        }else{
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment2).scaleType=ImageView.ScaleType.CENTER_CROP}
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment2).setImageBitmap(bitmap)}
            picList[map.get("position") as Int].pic2=bitmap
        }
    }

    var name=""
    private fun getQcode(content: String) {
        HttpManager.getQcode(content).request(this@InspectSelectActivity){ _,data->
            data.let {
                tv_district.text = it!![0].district
                tv_town.text = it!![0].town
                tv_village.text = it!![0].village
                name=it!![0].district+" "+it!![0].town+" "+it!![0].village
                siteId=it!![0].id
                val latLngServer=LatLng(it[0].lat,it[0].lng)
                val LatLngLocal= LatLng(MapLocationUtil.instance.lat,MapLocationUtil.instance.lng)
                var distince=DistanceUtil.getDistance(latLngServer,LatLngLocal)
                if (distince>500){
                    tv_district.textColor = Color.RED
                    tv_town.textColor = Color.RED
                    tv_village.textColor = Color.RED
                }
            }
        }
    }

    var map=HashMap<String,Any>()

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetMessage(data: Message) {
        map= data.message as HashMap<String, Any>
    }

}
