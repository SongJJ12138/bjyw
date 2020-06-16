package com.hbcx.bjckyh.ui

import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.hbcx.bjckyh.R
import kotlinx.android.synthetic.main.activity_create_order.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.hbcx.bjckyh.utils.date2String
import java.util.*


class CreateOrderActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        createOrder_spinner_year.gravity = Gravity.CENTER
        initView()
        initClick()
    }

    private fun initView() {
    }
    private fun initClick() {
        createOrder_spinner_month.onClick {
            val pvTime = TimePickerBuilder(this@CreateOrderActivity,
                OnTimeSelectListener { date, _ ->
                    val str=date2String(date)
                    val time=str.split("-")
                    createOrder_spinner_year.text=time[0]
                    createOrder_spinner_month.text= time[1]
                    createOrder_spinner_day.text=time[2]
                })
             .setCancelText("取消")//取消按钮文字
             .setSubmitText("确认")//确认按钮文字
             .build()
            pvTime.show()
        }
    }

}
