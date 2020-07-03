package com.bjyw.bjckyh.adapter

import android.graphics.Color
import android.widget.TextView
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.daobean.Inspect
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

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
        var toast=holder.bind<TextView>(R.id.a)
        if (data.status.equals("5")){
            toast.text="待上传"
            toast.textColor= Color.RED
        }else if(data.status.equals("6")){
            toast.text="已完成"
            toast.textColor=context.resources.getColor(R.color.darkgrey)
        }else{
            toast.text="待完成"
            toast.textColor=context.resources.getColor(R.color.toast_color)
            holder.itemView.onClick {
                listener.onChoose(data.orderIndex,data.siteId,data.is_unusual,data.status)
            }
        }

    }
}