package com.bjyw.bjckyh.ui

import android.graphics.Color
import android.os.Bundle
import com.bjyw.bjckyh.fragment.InspectFragment
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.fragment.RepairFragment
import kotlinx.android.synthetic.main.activity_inspect_detail.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor


class InspectDetailActivity : BaseActivity() {
    private val inspect by lazy {
        InspectFragment.newInstance(equipId)
    }
    private val repair by lazy {
        RepairFragment()
    }
    private val equipId by lazy {
        intent.getStringExtra("equipId")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_detail)
        activity_include_tvrignt.text="提交"
        activity_include_tvrignt.textColor= Color.WHITE
        val fT = supportFragmentManager.beginTransaction()
        fT.add(R.id.details_framlayout, repair)
        fT.add(R.id.details_framlayout, inspect)
        fT.hide(repair).show(inspect).commit()
        initClick()
    }


    private fun initClick() {
        tv_equipdetails_repair.onClick {
            tv_equipdetails_inspect.background=application.resources.getDrawable(R.drawable.tv_equiptitle2)
            tv_equipdetails_repair.background=application.resources.getDrawable(R.drawable.tv_equiptitle)
            val fT = supportFragmentManager.beginTransaction()
            fT.hide(inspect).show(repair).commit()
        }
        tv_equipdetails_inspect.onClick {
            tv_equipdetails_repair.background=application.resources.getDrawable(R.drawable.tv_equiptitle2)
            tv_equipdetails_inspect.background=application.resources.getDrawable(R.drawable.tv_equiptitle)
            val fT = supportFragmentManager.beginTransaction()
            fT.hide(repair).show(inspect).commit()
        }
    }


    override fun onDestroy() {
        try {
            super.onDestroy()
        }catch (e:NullPointerException){}

    }
}



