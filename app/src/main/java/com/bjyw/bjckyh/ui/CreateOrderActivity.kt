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
import com.bjyw.bjckyh.bean.Site2
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
    private val disList=ArrayList<Site2>()
    private val townList=ArrayList<List<Site2>>()
    private val villageList=ArrayList<List<List<Site2>>>()
    private val SiteList=ArrayList<Site>()
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
                        var dis=Site2(site.id,site.name)
                        disList.add(dis)
                    }
                    SiteList.add(site)
                }
                disList.forEach {dis ->
                    var towns=ArrayList<Site2>()
                    dataBean.forEach {
                        if (it.parent_id==dis.id){
                            var town=Site2(it.id,it.name)
                            towns.add(town)
                        }
                    }
                    townList.add(towns)
                }

                townList.forEach {
                    var villagess=ArrayList<List<Site2>>()
                    it.forEach {town->
                        var villages=ArrayList<Site2>()
                       dataBean.forEach {
                           if (it.parent_id==town.id){
                               var village=Site2(it.id,it.name)
                               villages.add(village)
                           }
                       }
                        villagess.add(villages)
                    }
                    villageList.add(villagess)
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
            createOrder_spinner_district.text=disList[options1].name
            createOrder_spinner_town.text=townList[options1][options2].name
            createOrder_spinner_village.text=villageList[options1][options2][options3].name
            var Id=villageList[options1][options2][options3].id
            SiteList.forEach {
                if (it.id==Id){
                    villageId=it.site_index
                }
            }
        })
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .build<Any>()
        var dislist=ArrayList<String>()
        var townlist=ArrayList<List<String>>()
        var villagelist=ArrayList<List<List<String>>>()
        disList.forEach {
            dislist.add(it.name)
        }
        townList.forEach {
            var list=ArrayList<String>()
            it.forEach {
                list.add(it.name)
            }
            townlist.add(list)
        }
        villageList.forEach {
            var list2=ArrayList<List<String>>()
            it.forEach {sites ->
                var list=ArrayList<String>()
                sites.forEach {site->
                    list.add(site.name)
                }
                list2.add(list)
            }
            villagelist.add(list2)
        }
        pvOptions.setPicker(dislist as List<String>?, townlist as List< List<String>>?, villagelist as List<List< List<String>>>?)
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
