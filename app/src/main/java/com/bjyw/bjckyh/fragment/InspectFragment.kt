package com.bjyw.bjckyh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.EquipInspectBean
import com.bjyw.bjckyh.bean.daobean.InspectEquipMent
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import kotlinx.android.synthetic.main.equip_inspect.*
import kotlinx.android.synthetic.main.fragmeny_repair.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.lang.StringBuilder

class InspectFragment: BaseFragment() {
    private var checkId=""
    private var isExit="-1"
    var isRepair=-1

    companion object {
        fun newInstance(equipId:String,typeId:String,orderId:String): InspectFragment {
            val fragment = InspectFragment()
            val bundle = Bundle()
            bundle.putString("equipId",equipId)
            bundle.putString("typeId",typeId)
            bundle.putString("orderId",orderId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var equipId:String
        var orderId:String
        var typeId:String
        if (arguments == null) {
            equipId = ""
            orderId=""
            typeId=""
        } else {
            orderId = arguments?.getString("orderId")?:""
            equipId = arguments?.getString("equipId")?:""
            typeId = arguments?.getString("typeId")?:""
        }
        getData(equipId,typeId,orderId)
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rb_have.onClick {
            isExit="0"
            title_yichang.visibility=View.VISIBLE
            rg_equip_inspect.visibility=View.VISIBLE
        }
        rb_Nhave.onClick {
            isExit="1"
            title_yichang.visibility=View.GONE
            rg_equip_inspect.visibility=View.GONE
        }
        rb_repair_ok.onClick {
            isRepair=0
        }
        rb_repair_yiliu.onClick{
            isRepair=1
        }
    }

    override fun onFirstVisibleToUser() {}

    override fun contentViewId() = R.layout.equip_inspect

    fun getDataBean(): EquipInspectBean {
        return EquipInspectBean(""+isRepair,isExit,checkId)
    }

    private fun getData(equipId:String,typeId:String,orderId:String) {
        HttpManager.getEquipUsual(typeId).requestByF(this){ _, data->
            data?.let {list ->
                var tishi= StringBuilder()
                for (i in 0 until list!!.size){
                    var inspectEquipMent= InspectEquipMent()
                    inspectEquipMent.equipmentIndex=equipId
                    inspectEquipMent.comments=""
                    inspectEquipMent.context=""
                    inspectEquipMent.is_exist="0"
                    inspectEquipMent.is_unusual="0"
                    inspectEquipMent.orderIndex=orderId
                    inspectEquipMent.picture=""
                    inspectEquipMent.remark=""
                    var radioButton= RadioButton(context)
                    radioButton.text=list[i].abnormalContext
                    radioButton.textSize=14f
                    radioButton.onClick {
                        checkId=""+list[i].id
                    }
                    rg_equip_inspect.addView(radioButton)
                    var a=i+1
                    tishi.append(""+a+"丶 "+list[i].normalContext+"\n")
                }
                tv_equip_inspect.text=tishi
            }
        }
    }
}