package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Order
import org.jetbrains.anko.sdk25.coroutines.onClick

class OrderAdapter (mData:ArrayList<Order>, private val listener: onClickListener):HFRecyclerAdapter<Order>(mData,
    R.layout.item_order){
    interface onClickListener{
        fun onClick(equipId:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Order) {
        holder.setText(R.id.tv_type,data.typeName+"工单")
        holder.setText(R.id.tv_time,data.createTime)
        holder.setText(R.id.tv_site,data.districtName+" "+data.townName+" "+data.villageName)
        holder.itemView.onClick {
            listener.onClick(data.id)
        }
    }
}