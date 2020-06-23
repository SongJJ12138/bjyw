package com.bjyw.bjckyh.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
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
import com.bjyw.bjckyh.utils.FileProviderUtil
import com.lcw.library.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.item_workstatus.view.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.io.File


class EnvironUsualView : LinearLayout {
    private val REQUEST__CODE_IMAGES = 0x03
    private lateinit var activity:InspectSelectActivity
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

    @SuppressLint("SimpleDateFormat")
    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.item_workstatus, this, true)
        img_environment1.onClick {
            var path_name =
                "image" + Math.round((Math.random() * 9 + 1) * 100000) + ".jpg"
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var file= File(
                Environment.getExternalStorageDirectory(),
                path_name
            )
            uri= FileProviderUtil.getFileUri(
                context,
                file,
                activity.getPackageName() + ".fileprovider"
            )!!
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
            activity.startActivityForResult(intent,REQUEST__CODE_IMAGES)
            var map=HashMap<String,Any>()
            map["position"] = position
            map["type"] = 0
            map["uri"]=file.absolutePath
            EventBus.getDefault().post(Message.getInstance(map))
        }


        img_environment2.onClick {
                var path_name =
                    "image" + Math.round((Math.random() * 9 + 1) * 100000) + ".jpg"
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var file= File(
                    Environment.getExternalStorageDirectory(),
                    path_name
                )
                uri= FileProviderUtil.getFileUri(
                    context,
                    file,
                    activity.getPackageName() + ".fileprovider"
                )!!
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri)
                activity.startActivityForResult(intent,REQUEST__CODE_IMAGES)
                var map=HashMap<String,Any>()
                map["position"] = position
                map["type"] = 1
                map["uri"]=file.absolutePath
                EventBus.getDefault().post(Message.getInstance(map))
        }

        bt_environment.onClick {
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

        }
    }

    fun init(baseActivity:InspectSelectActivity, position:Int,conId:Int){
        activity=baseActivity
        this.position =position
        this.conId =conId
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
                activity.toast(list[i].id.toString())
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
            environ.is_unusual="1"
        }
        return  environ
    }

}
