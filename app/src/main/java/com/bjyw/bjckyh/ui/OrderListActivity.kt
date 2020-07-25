package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.fragment.ChuliFragment
import com.bjyw.bjckyh.fragment.RepairFragment
import com.bjyw.bjckyh.fragment.WanchengFragment
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

class OrderListActivity : BaseActivity() {
    private val chuli by lazy {
        ChuliFragment()
    }
    private val wancheng by lazy {
        WanchengFragment()
    }
    private val isSuccess by lazy {
        intent.getIntExtra("isSuccess",0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        activity_include_tvrignt.text="刷新"
        activity_include_tvrignt.textColor= Color.WHITE
        val fT = supportFragmentManager.beginTransaction()
        fT.add(R.id.orderlist_framlayout, chuli)
        fT.add(R.id.orderlist_framlayout, wancheng)
        fT.hide(wancheng).show(chuli).commit()
        initClick()
    }

    private fun initClick() {
        activity_include_tvrignt.onClick {
            chuli.refreash()
        }
        activity_include_btback.onClick {
            if (isSuccess==0){
                var intent= Intent(this@OrderListActivity,InspectSelectActivity::class.java)
                setResult(Activity.RESULT_CANCELED,intent)
                finish()
            }else{
                var intent= Intent(this@OrderListActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
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

    override fun onBackPressed() {
        if (isSuccess==0){
            var intent= Intent(this@OrderListActivity,InspectSelectActivity::class.java)
            setResult(Activity.RESULT_CANCELED,intent)
            finish()
        }else{
            var intent= Intent(this@OrderListActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        try {
            super.onDestroy()
        }catch (e:NullPointerException){}

    }
}
