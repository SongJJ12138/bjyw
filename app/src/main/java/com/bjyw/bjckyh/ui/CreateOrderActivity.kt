package com.bjyw.bjckyh.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateUtils
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
import com.bjyw.bjckyh.dialog.CreateOrderDialog
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.date2String
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList





class CreateOrderActivity : BaseActivity(), CreateOrderDialog.onClickListener {
    override fun onClick() {
        creayeOrder()
    }

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
        activity_include_tvrignt.text="工单列表"
        activity_include_tvrignt.textColor=Color.WHITE
        activity_include_btback.onClick {
            this@CreateOrderActivity.finish()
        }
        activity_include_tvrignt.onClick {
            startActivity(Intent(this@CreateOrderActivity,OrderListActivity::class.java))
        }
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
            var dialog=CreateOrderDialog(this@CreateOrderActivity,this@CreateOrderActivity)
           dialog.show()
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
            villageId=villageSiteList[options3].site_index
        })
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .build<Any>()
        pvOptions.setNPicker(disList as List<Any>?, townList as List<Any>?, villageList as List<Any>?)
        pvOptions.show()
    }

    private fun showTimePicker() {
        val startDate = Calendar.getInstance()
        startDate.set( startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH))
        val endDate = Calendar.getInstance()
        endDate.set(2050, 1, 1)
        val pvTime = TimePickerBuilder(this@CreateOrderActivity,
            OnTimeSelectListener { date, _ ->
                val str=date2String(date)
                val time=str.split("-")
                if ( DateUtils.isToday(date.time)){
                    toast("不可选择今日巡检")
                    return@OnTimeSelectListener
                }
                createOrder_spinner_year.text=time[0]
                createOrder_spinner_month.text= time[1]
                createOrder_spinner_day.text=time[2]
                planTime= "$str 07:00:00"
            })
            .setRangDate(startDate,endDate)
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .build()
        pvTime.show()
    }

}
