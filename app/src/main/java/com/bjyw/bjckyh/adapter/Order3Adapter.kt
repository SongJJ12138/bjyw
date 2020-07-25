package com.bjyw.bjckyh.adapter

import android.graphics.Color
import android.widget.TextView
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Order
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

class Order3Adapter (mData:ArrayList<Order>):HFRecyclerAdapter<Order>(mData,
    R.layout.item_order){
    interface onClickListener{
        fun onClick(equipId:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Order) {
        var tv=holder.bind<TextView>(R.id.a)
        tv.text="已完成"
        tv.textColor=context.resources.getColor(R.color.gray)
        holder.setText(R.id.tv_type,data.typeName+"工单")
        holder.setText(R.id.tv_time,data.createTime)
        holder.setText(R.id.tv_site,data.districtName+" "+data.townName+" "+data.villageName)
    }
}