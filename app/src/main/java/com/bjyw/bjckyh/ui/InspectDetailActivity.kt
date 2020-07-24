package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.bjyw.bjckyh.fragment.InspectFragment
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.EquipInspectBean
import com.bjyw.bjckyh.bean.daobean.InspectConsumable
import com.bjyw.bjckyh.bean.daobean.InspectEquipMent
import com.bjyw.bjckyh.fragment.RepairFragment
import com.bjyw.bjckyh.utils.DbController
import kotlinx.android.synthetic.main.activity_inspect_detail.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast


class InspectDetailActivity : BaseActivity() {
    var status=-1
    private val inspect by lazy {
        InspectFragment.newInstance(equipId,typeId,orderId)
    }

    private val equipId by lazy {
        intent.getStringExtra("equipId")
    }
    private val repair by lazy {
        RepairFragment()
    }
    private val typeId by lazy {
        intent.getStringExtra("typeId")
    }
    private val name by lazy {
        intent.getStringExtra("name")
    }
    private val position by lazy {
        intent.getIntExtra("position",0)
    }


    private val orderId by lazy {
        intent.getStringExtra("orderId")
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
        activity_include_btback.onClick {
            finish()
        }
        activity_include_tvrignt.onClick {
            saveData()
        }
        tv_equipdetails_repair.onClick {
            status++
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

    private fun saveData() {
        var equipInspectBean=inspect.getDataBean()
        if (equipInspectBean.is_exist.equals("-1")){
            toast("请选择设备情况")
            return
        }else if(equipInspectBean.context.equals("0")&&equipInspectBean.context.equals("")){
            toast("未选择异常问题")
            return
        }
        if (status<0){
            toast("请检查是否需要设备维修")
            return
        }
        var equipRepairBean=repair.getDataBean()
        if (equipInspectBean.content.equals("-1")){
            toast("请选择维修情况")
            return
        }
        showDialog()
        equipRepairBean.consumable.forEach {
            var inspectConsumable=InspectConsumable()
            inspectConsumable.orderIndex=orderId
            inspectConsumable.equipId=equipId
            inspectConsumable.consumableId=it.consumableId
            inspectConsumable.count=it.count
            DbController.getInstance(applicationContext).insertOrReplaceConsum(inspectConsumable)
        }
        var inspectEquipMent=InspectEquipMent()
        inspectEquipMent.equipName=name
        inspectEquipMent.position=position
        inspectEquipMent.equipmentIndex=equipId
        inspectEquipMent.comments=""
        inspectEquipMent.context=equipInspectBean.context
        inspectEquipMent.is_exist=equipInspectBean.is_exist
        if (equipInspectBean.context.equals("")){
            inspectEquipMent.is_unusual="0"
        }else{
            if (equipInspectBean.content.equals("1")){
                inspectEquipMent.is_unusual="1"
            }else{
                inspectEquipMent.is_unusual="0"
            }
        }
        inspectEquipMent.orderIndex=orderId
        inspectEquipMent.picture=equipRepairBean.picture
        inspectEquipMent.remark=equipRepairBean.remark
        DbController.getInstance(applicationContext).insertOrReplaceEquipment(inspectEquipMent)
        var type=0
        if (inspectEquipMent.is_exist.equals("1")){
            type=2
        }else if(inspectEquipMent.is_unusual.equals("1")){
            type=1
        }
        var intent=Intent(this@InspectDetailActivity,InspectMainActivity::class.java)
        intent.putExtra("position",position)
        intent.putExtra("type",type)
        setResult(Activity.RESULT_OK,intent)
        dismissDialog()
        this.finish()
    }


    override fun onDestroy() {
        try {
            super.onDestroy()
        }catch (e:NullPointerException){}

    }
}



