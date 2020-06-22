package com.bjyw.bjckyh.ui

import android.os.Bundle
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.fragment.ChuliFragment
import com.bjyw.bjckyh.fragment.RepairFragment
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class OrderListActivity : BaseActivity() {
    private val chuli by lazy {
        ChuliFragment()
    }
    private val wancheng by lazy {
        RepairFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        activity_include_tvrignt.text=""
        val fT = supportFragmentManager.beginTransaction()
        fT.add(R.id.orderlist_framlayout, chuli)
        fT.add(R.id.orderlist_framlayout, wancheng)
        fT.hide(wancheng).show(chuli).commit()
        initClick()
    }
    private fun initClick() {
        tv_orderlist_chuli.onClick {
            tv_orderlist_wancheng.background=application.resources.getDrawable(R.drawable.tv_equiptitle2)
            tv_orderlist_chuli.background=application.resources.getDrawable(R.drawable.tv_equiptitle)
            val fT = supportFragmentManager.beginTransaction()
            fT.hide(wancheng).show(chuli).commit()
        }
        tv_orderlist_wancheng.onClick {
            tv_orderlist_chuli.background=application.resources.getDrawable(R.drawable.tv_equiptitle2)
            tv_orderlist_wancheng.background=application.resources.getDrawable(R.drawable.tv_equiptitle)
            val fT = supportFragmentManager.beginTransaction()
            fT.hide(chuli).show(wancheng).commit()
        }
    }


    override fun onDestroy() {
        try {
            super.onDestroy()
        }catch (e:NullPointerException){}

    }
}
