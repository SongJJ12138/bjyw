package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Consumable

class ConsumableAdapter(mData:ArrayList<Consumable>):HFRecyclerAdapter<Consumable>(mData,
    R.layout.item_consumable){
    override fun onBind(holder: ViewHolder, position: Int, data: Consumable) {
        holder.setText(R.id.name,data.name)
        holder.setText(R.id.guige,data.unitPrice+"元/"+data.unit)
    }
}