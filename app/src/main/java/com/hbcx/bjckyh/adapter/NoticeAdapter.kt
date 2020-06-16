package com.hbcx.bjckyh.adapter

import com.hbcx.bjckyh.R
import com.hbcx.bjckyh.bean.Notify

class NoticeAdapter(mData:ArrayList<Notify>): HFRecyclerAdapter<Notify>(mData,
    R.layout.item_notice
) {
    override fun onBind(holder: ViewHolder, position: Int, data: Notify) {
        holder.setText(R.id.item_notice_title,data.title)
        holder.setText(R.id.item_notice_time,data.recordTimeFormat)
        holder.setText(R.id.item_notice_content,data.context)
        holder.setText(R.id.item_notice_type,data.type)
    }
}