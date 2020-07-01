package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
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
import java.io.FileInputStream
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
        var threadCount=3
        showDialog()
        HttpManager.getCoition(0).request(this@InspectSelectActivity){ _,data->
            data.let { data->
                threadCount--
                data!!.forEach {inspectItem ->
                    var radioButton=RadioButton(applicationContext)
                    radioButton.text=inspectItem.context
                    radioButton.textSize=14f
                    radioButton.onClick {
                        isOk = inspectItem.context == "正常巡检"
                        environList.forEach {
                            it.setOk(isOk)
                        }
                        coitionId=""+inspectItem.index
                    }
                    radioButton.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                    )
                    group_tiaojian.addView(radioButton)
                }
                if (threadCount==0){
                    dismissDialog()
                }
            }
        }
        HttpManager.getCoition(1).request(this@InspectSelectActivity){ _,data->
            data.let {data ->
                threadCount--
                for (i in 0 until data!!.size) {
                    var environUsualView=EnvironUsualView(applicationContext)
                    environUsualView.init(this@InspectSelectActivity,i,data[i].index,isOk)
                    environUsualView.setTitle(data[i].context)
                    layout_EnvironUsualView.addView(environUsualView)
                    environList.add(environUsualView)
                    var pic=environPic(null,null)
                    picList.add(pic)
                }
                if (threadCount==0){
                    dismissDialog()
                }
            }
        }
        HttpManager.getUseStatus(6).request(this@InspectSelectActivity){ _,data->
            data.let {
                threadCount--
                data!!.forEach {useStatus ->
                    var radioButton=RadioButton(applicationContext)
                    radioButton.text=useStatus.abnormalContext
                    radioButton.textSize=14f
                    radioButton.onClick {
                        useStutusId=""+useStatus.index

                    }
                    radioButton.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                    )
                    group_status.addView(radioButton)
                }
                if (threadCount==0){
                    dismissDialog()
                }
            }
        }
    }


    private fun initClick() {
        bt_sitehistory.onClick {
            var intent=Intent(this@InspectSelectActivity,SiteDeatilActivity::class.java)
            //equipStatus
            intent.putExtra("siteId","1")
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
                    list.add(convertBitmapToFile(applicationContext,picList[i].pic1!!))
                }else if (picList[i].pic1!=null){
                    list.add(convertBitmapToFile(applicationContext,picList[i].pic2!!))
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
        activity_include_tvrignt.text="工单列表"
        activity_include_tvrignt.textColor=Color.WHITE
        val formatter = SimpleDateFormat("yyyy年MM月dd日")
        val curDate = Date(System.currentTimeMillis())
        tv_inspecttime.text=formatter.format(curDate)
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
            if (data==null){
                showPic("")
            }else{
                val paths = data!!.extras!!.getSerializable("photos") as List<String>? //path是选择拍照或者图片的地址数组
                paths?.get(0)?.let { showPic(it) }
            }
        }
    }

    private fun showPic(s: String) {
        val uri = TakePhoto.getOutputMediaFileUri(applicationContext)
        val bitmap = TakePhoto.getBitmapFormUri(applicationContext, uri)
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
