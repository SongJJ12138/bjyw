package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.daobean.Inspect
import org.jetbrains.anko.sdk25.coroutines.onClick

class Order2Adapter  (mData:ArrayList<Inspect>, private val listener: onClickListener):HFRecyclerAdapter<Inspect>(mData,
    R.layout.item_order){
    interface onClickListener{
        fun onChoose(
            orderId: String,
            siteId: Int,
            is_unusual: String,
            status1: String
        )
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Inspect) {
        holder.setText(R.id.tv_type,"巡检工单")
        holder.setText(R.id.tv_site,data.name)
        holder.setText(R.id.tv_time,data.time)
        holder.setText(R.id.a,"待完成")
        holder.itemView.onClick {
            listener.onChoose(data.orderIndex,data.siteId,data.is_unusual,data.status)
        }
    }
}