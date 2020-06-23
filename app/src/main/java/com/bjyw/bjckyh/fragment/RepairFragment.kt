package com.bjyw.bjckyh.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.ConsumableAdapter
import com.bjyw.bjckyh.bean.Consumable
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.requestByF
import kotlinx.android.synthetic.main.fragmeny_repair.*


class RepairFragment : BaseFragment(){
    override fun contentViewId()=R.layout.fragmeny_repair

    override fun onFirstVisibleToUser() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragmeny_repair, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_consumable.isNestedScrollingEnabled = false
        rv_consumable.layoutManager=LinearLayoutManager(context)
        rv_consumable.adapter=adapter
        getData()
    }


    var list=ArrayList<Consumable>()
    private val adapter by lazy{
        ConsumableAdapter(list)
    }

    private fun getData() {
        HttpManager.getConsumable().requestByF(this){ _, data->
            data?.let {
                list.clear()
                list.addAll(it)
            }
        }
    }


}