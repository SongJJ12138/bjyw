package com.bjyw.bjckyh.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.EquipAdapter
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import kotlinx.android.synthetic.main.activity_inspect_main.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.textColor

class InspectMainActivity : BaseActivity(), EquipAdapter.onClickListener {
    override fun onClick(equipId: Int) {
        var intent=Intent(this@InspectMainActivity,InspectDetailActivity::class.java)
        intent.putExtra("equipId",""+equipId)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_main)
        initView()
        getEquipUsual()
    }

    private val siteId by lazy{
        intent.getIntExtra("siteId",0)
    }
    private val orderId by lazy{
        intent.getStringExtra("orderId")
    }
    private fun initView() {
        activity_include_tvrignt.text="保存"
        activity_include_tvrignt.textColor=Color.WHITE
        rv_inspect_equip.layoutManager= GridLayoutManager(applicationContext,3)
        rv_inspect_equip.adapter=adapter
    }

    var list=ArrayList<Equip>()
    private val adapter by lazy {
        EquipAdapter(list,this)
    }
    private fun getEquipUsual() {
        showDialog()
        HttpManager.getAllEquip(siteId).request(this@InspectMainActivity){ _, data->
            data.let {
                list.addAll(it!!)
                adapter.notifyDataSetChanged()
                dismissDialog()
            }
        }
    }
}
