package com.bjyw.bjckyh.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Message
import com.bjyw.bjckyh.bean.UseSttus
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.ui.InspectSelectActivity
import com.bjyw.bjckyh.utils.TakePhoto
import kotlinx.android.synthetic.main.item_workstatus.view.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast


class EnvironUsualView : LinearLayout {
    private val REQUEST__CODE_IMAGES = 0x03
    private lateinit var activity:InspectSelectActivity
    private var isOk=true
    private var conId= 0
    private var checkId= 0
    private var position=0
    private var list=ArrayList<UseSttus>()
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    lateinit var uri: Uri
    var isYiliu=true
    @SuppressLint("SimpleDateFormat")
    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.item_workstatus, this, true)
        rb_ok.onClick {
            isYiliu=false
        }
        rb_yiliu.onClick {
            isYiliu=true
        }
        img_environment1.onClick {
            val fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            val myuri: Uri = TakePhoto.getOutputMediaFileUri(context,fileName)
            var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
            activity.startActivityForResult(intent, REQUEST__CODE_IMAGES)
            var map=HashMap<String,Any>()
            map["position"] = position
            map["type"] = 0
            map["uri"]=myuri
            map["name"]=fileName
            EventBus.getDefault().post(Message.getInstance(map))
        }


        img_environment2.onClick {
            val fileName = "IMG_" + System.currentTimeMillis() + ".jpg"
            val myuri: Uri = TakePhoto.getOutputMediaFileUri(context,fileName)
            var intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)// 启动系统相机
            intent.putExtra(MediaStore.EXTRA_OUTPUT, myuri)
            activity.startActivityForResult(intent, REQUEST__CODE_IMAGES)
            var map=HashMap<String,Any>()
            map["position"] = position
            map["type"] = 1
            map["uri"]=myuri
            map["name"]=fileName
            EventBus.getDefault().post(Message.getInstance(map))
        }

        bt_environment.onClick {
            if (isOk){
                if (layout_environment.visibility== View.GONE){
                    if (list.size>0){
                        initSelect()
                    }else{
                        activity.showDialog()
                        getData()
                    }
                }else{
                    layout_environment.visibility= View.GONE
                }
            }else{
                activity.toast("异常巡检，无法点击")
            }
        }
    }

    fun init(baseActivity:InspectSelectActivity, position:Int,conId:Int,isOk:Boolean){
        activity=baseActivity
        this.position =position
        this.conId =conId
        this.isOk=isOk
    }
    private fun getData() {
        HttpManager.getUseStatus(conId).request(activity){ _, data->
            data.let {
                list.addAll(it!!)
                initSelect()
                activity.dismissDialog()
            }
        }
    }

    private fun initSelect() {
        var tishi=StringBuilder()
        rg_environment.removeAllViews()
        for (i in 0 until list!!.size){
            var radioButton=RadioButton(context)
            radioButton.text=list[i].abnormalContext
            radioButton.textSize=14f
            radioButton.onClick {
                checkId=list[i].id
            }
            rg_environment.addView(radioButton)
            var a=i+1
            tishi.append(""+a+"丶 "+list[i].normalContext+"\n")
        }
        tv_environment.text=tishi
        layout_environment.visibility= View.VISIBLE
    }

    fun setTitle(title:String){
        tv_title.text=title
    }

    fun getEnvironBean():InspectEnvironMent {
        var environ=InspectEnvironMent()
        environ.environmentIndex=""+conId
        environ.remark=ed_environmentcontent.text.toString().trim()
        environ.picture=""
        if (checkId==0){
            environ.context=""
            environ.is_unusual="0"
        }else{
            environ.context=""+checkId
            if (isYiliu){
                environ.is_unusual="1"
            }else{
                environ.is_unusual="0"
            }
        }
        return  environ
    }

    fun setOk(ok: Boolean) {
        isOk=ok
        if (!isOk){
            tv_title.textColor=Color.RED
            if (layout_environment.visibility== View.VISIBLE){
                layout_environment.visibility= View.GONE
            }
        }else{
            tv_title.textColor=resources.getColor(R.color.grey)
        }
    }

}
