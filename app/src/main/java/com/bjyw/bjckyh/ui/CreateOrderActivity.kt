package com.bjyw.bjckyh.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bjyw.bjckyh.R
import kotlinx.android.synthetic.main.activity_create_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bjyw.bjckyh.bean.Site
import com.bjyw.bjckyh.bean.daobean.Inspect
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.DbController
import com.bjyw.bjckyh.utils.date2String
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.toast
import kotlin.collections.ArrayList


class CreateOrderActivity : BaseActivity() {
    var villageId:Int=0
    var planTime:String=""
    private val disList=ArrayList<String>()
    private val townList=ArrayList<String>()
    private val villageList=ArrayList<String>()
    private val villageSiteList=ArrayList<Site>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        createOrder_spinner_year.gravity = Gravity.CENTER
        initClick()
        getSite()
        activity_include_tvrignt.visibility=View.GONE
    }

    private fun getSite() {
        HttpManager.getSite().request(this@CreateOrderActivity){ _,data ->
            data.let { dataBean ->
                dataBean!!.forEach {site ->
                    if (site.level==2){
                        disList.add(site.name)
                    }else if (site.level==3){
                        townList.add(site.name)
                    }else if (site.level==4){
                        villageList.add(site.name)
                        villageSiteList.add(site)
                    }
                }
            }
        }
    }
    private fun initClick() {
        activity_include_btback.onClick{
            this@CreateOrderActivity.finish()
        }
        createOrder_spinner_district.onClick {
            showOptionPicker()
        }
        createOrder_spinner_town.onClick {
            showOptionPicker()
        }
        createOrder_spinner_village.onClick {
           showOptionPicker()
        }
        createOrder_spinner_day.onClick {
            showTimePicker()
        }
        createOrder_spinner_month.onClick {
            showTimePicker()
        }
        createOrder_spinner_year.onClick {
            showTimePicker()
        }
        createOrder_tv_create.onClick {
            creayeOrder()
        }
    }

    private fun creayeOrder() {
        if (villageId==0){
            toast("请选择站点")
            return
        }
        if (planTime.equals("")){
            toast("请选择计划工作时间")
            return
        }
        showDialog()
        HttpManager.createOrder(villageId,planTime).request(this@CreateOrderActivity){ _,data ->
            data.let {
                toast("创建成功")
                this@CreateOrderActivity.finish()
            }
        }
    }

    private fun showOptionPicker() {
        val pvOptions =OptionsPickerBuilder(this@CreateOrderActivity, OnOptionsSelectListener {
                options1, options2, options3, _ ->
            createOrder_spinner_district.text=disList[options1]
            createOrder_spinner_town.text=townList[options2]
            createOrder_spinner_village.text=villageList[options3]
            villageId=villageSiteList[options3].id
            toast(villageList[options3]+":"+villageId)
        })
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .build<Any>()
        pvOptions.setNPicker(disList as List<Any>?, townList as List<Any>?, villageList as List<Any>?)
        pvOptions.show()
    }

    private fun showTimePicker() {
        val pvTime = TimePickerBuilder(this@CreateOrderActivity,
            OnTimeSelectListener { date, _ ->
                val str=date2String(date)
                val time=str.split("-")
                createOrder_spinner_year.text=time[0]
                createOrder_spinner_month.text= time[1]
                createOrder_spinner_day.text=time[2]
                planTime=time[0]+"-"+time[1]+"-"+time[2]+" 07:00:00"
                toast(planTime)
            })
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .build()
        pvTime.show()
    }

}
