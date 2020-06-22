package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.get
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.DistanceUtil
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Message
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.MapLocationUtil
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
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*


class InspectSelectActivity : BaseActivity() {
    private val REQUEST_CODE_SCAN=0x01
    private val REQUEST_CODE_ORDE=0x02
    private val REQUEST__CODE_IMAGES=0x03
    var siteId=""
    var coitionId=""
    var useStutusId=""
    var orderId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_select)
        orderId=intent.getStringExtra("orderId")
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
                        coitionId=""+inspectItem.index
                        toast(coitionId)
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
                    environUsualView.init(this@InspectSelectActivity,i)
                    environUsualView.setTitle(data[i].context)
                    layout_EnvironUsualView.addView(environUsualView)
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
                        useStutusId=""+useStatus.id
                        toast(useStutusId)
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
        activity_include_btback.onClick {
            this@InspectSelectActivity.finish()
        }
        activity_include_tvrignt.onClick {
            startActivityForResult(Intent(this@InspectSelectActivity,OrderListActivity::class.java),REQUEST_CODE_ORDE)
        }
        layout_saoma.onClick {
            if (orderId.equals("")){
                toast("请先去工单列表选择工单")
            }else{
                val intent = Intent(this@InspectSelectActivity, CaptureActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_SCAN)
            }
        }
        bt_next.onClick {
            var intent=Intent(this@InspectSelectActivity,InspectMainActivity::class.java)
            if (siteId == ""){
//                var inspect: Inspect = Inspect()
//                inspect.orderIndex= orderId
//                inspect.userId
//                DbController.getInstance(applicationContext).insert(inspect)
                intent.putExtra("siteId",siteId)
                startActivity(intent)
            }else{
                //测试
                toast("请先扫码获取站点数据")
                intent.putExtra("siteId",200)
                startActivity(intent)
            }
        }
    }

    private fun initView() {
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
                orderId= data.getStringExtra("orderIndex")
            }
        } else if(requestCode == REQUEST__CODE_IMAGES && resultCode == Activity.RESULT_OK){
            if (data==null){
                showPic("")
            }else{
                val paths = data!!.extras!!.getSerializable("photos") as List<String>? //path是选择拍照或者图片的地址数组
                paths?.get(0)?.let { showPic(it) }
            }

        }else{
            //测试
            getQcode("BJCKYH-000000085")
        }
    }

    private fun showPic(s: String) {
        var uriStr:String
        if (s.equals("")){
            uriStr= map.get("uri") as String
        }else{
            uriStr=s
        }
        var fis: FileInputStream? = null
        fis = FileInputStream(uriStr)
        val bitmap = BitmapFactory.decodeStream(fis)
        if (map.get("type")==0){
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment1).setImageBitmap(bitmap)}
        }else{
            map.get("position")?.let { layout_EnvironUsualView.get(it as Int).findViewById<ImageView>(R.id.img_environment2).setImageBitmap(bitmap)}
        }
    }

    private fun getQcode(content: String) {
        HttpManager.getQcode(content).request(this@InspectSelectActivity){ _,data->
            data.let {
                tv_district.text = it!![0].district
                tv_town.text = it!![0].town
                tv_village.text = it!![0].village
                siteId=""+it!![0].id
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
