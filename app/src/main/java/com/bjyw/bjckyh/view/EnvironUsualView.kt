package com.bjyw.bjckyh.view

import android.widget.RadioButton
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.UseSttus
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.ui.InspectSelectActivity
import com.lcw.library.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.item_workstatus.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.lang.StringBuilder


class EnvironUsualView : LinearLayout {
    private val REQUEST__CODE_IMAGES = 0x03
    private lateinit var activity:InspectSelectActivity
    private var checkId= 0
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


    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.item_workstatus, this, true)
        img_environment1.onClick {
            ImagePicker.getInstance()
                .setTitle("选择照片")//设置标题
                .showCamera(true)//设置是否显示拍照按钮
                .showImage(false)//设置是否展示图片
                .showVideo(false)//设置是否展示视频
                .setSingleType(true)//设置图片视频不能同时选择
                .start(
                    activity,
                    REQUEST__CODE_IMAGES
                )
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

    fun init(baseActivity:InspectSelectActivity){
        activity=baseActivity
    }
    private fun getData() {
        HttpManager.getUseStatus(4).request(activity){ _, data->
            data.let {
                list.addAll(it!!)
                initSelect()
                activity.dismissDialog()
            }
        }
    }

    private fun initSelect() {
        var tishi=StringBuilder()
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

}
