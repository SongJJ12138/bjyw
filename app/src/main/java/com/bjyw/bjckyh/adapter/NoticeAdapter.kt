package com.bjyw.bjckyh.adapter

import android.content.Context
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Notify
import com.bjyw.bjckyh.dialog.NotifyDialog
import org.jetbrains.anko.sdk25.coroutines.onClick

class NoticeAdapter(val mContext: Context, mData:ArrayList<Notify>): HFRecyclerAdapter<Notify>(mData,
    R.layout.item_notice
) {
    override fun onBind(holder: ViewHolder, position: Int, data: Notify) {
        holder.setText(R.id.item_notice_title,data.title)
        holder.setText(R.id.item_notice_time,data.recordTimeFormat)
        holder.setText(R.id.item_notice_content,data.context)
        holder.setText(R.id.item_notice_type,data.type)
        holder.itemView.onClick {
            var notifyDialog=NotifyDialog(mContext,data)
            notifyDialog.show()
        }
    }
}