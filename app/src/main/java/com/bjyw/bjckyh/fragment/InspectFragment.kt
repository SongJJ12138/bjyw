package com.bjyw.bjckyh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import kotlinx.android.synthetic.main.equip_inspect.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.lang.StringBuilder

class InspectFragment: BaseFragment() {
    private var checkId= 0
    companion object {
        fun newInstance(equipId:String): Fragment {
            val fragment = InspectFragment()
            val bundle = Bundle()
            bundle.putString("equipId",equipId)
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
        if (arguments == null) {
            equipId = ""
        } else {
            equipId = arguments?.getString("equipId")?:""
        }
        getData(equipId)
        return super.onCreateView(inflater, container, savedInstanceState)

    }
    override fun onFirstVisibleToUser() {}

    override fun contentViewId() = R.layout.equip_inspect

    private fun getData(equipId:String) {
        HttpManager.getEquipUsual(equipId).requestByF(this){ _, data->
            data?.let {list ->
                var tishi= StringBuilder()
                for (i in 0 until list!!.size){
                        var radioButton= RadioButton(context)
                        radioButton.text=list[i].abnormalContext
                        radioButton.textSize=14f
                        radioButton.onClick {
                            checkId=list[i].id
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