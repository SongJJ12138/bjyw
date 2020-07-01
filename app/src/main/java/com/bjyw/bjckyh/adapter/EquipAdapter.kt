package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.EquipBean
import org.jetbrains.anko.sdk25.coroutines.onClick

class EquipAdapter(mData:ArrayList<EquipBean>, private val listener: onClickListener):HFRecyclerAdapter<EquipBean>(mData,
    R.layout.item_equip){
    interface onClickListener{
        fun onClick(position: Int,equipId:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: EquipBean) {
        holder.setText(R.id.item_tv_equip,data.name)
        holder.itemView.onClick {
            listener.onClick(position,""+data.type_index)
        }
    }
}