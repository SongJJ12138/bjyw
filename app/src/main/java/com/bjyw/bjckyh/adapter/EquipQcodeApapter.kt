package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Equip
import org.jetbrains.anko.sdk25.coroutines.onClick

class EquipQcodeApapter(mData:ArrayList<Equip>, private val listener: onClickListener):HFRecyclerAdapter<Equip>(mData,
    R.layout.item_equip){
    interface onClickListener{
        fun onClick(position: Int,equipId:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Equip) {
        holder.setText(R.id.item_tv_equip,data.name)
        holder.itemView.onClick {

        }
    }
}