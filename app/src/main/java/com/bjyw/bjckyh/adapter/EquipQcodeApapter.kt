package com.bjyw.bjckyh.adapter

import android.graphics.Color
import android.widget.TextView
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Equip
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

class EquipQcodeApapter(mData:ArrayList<Equip>, private val listener: onClickListener,val isChange:Boolean):HFRecyclerAdapter<Equip>(mData,
    R.layout.item_equip){
    interface onClickListener{
        fun onClick(equipQcode:String)
        abstract fun onChange(id: Int, position: Int)
        abstract fun onAdd(id: Int, position: Int)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Equip) {
        var tv=holder.bind<TextView>(R.id.item_tv_equip)
        tv.text=data.name
        if (!data.qrcode.equals("")){
            tv.textColor=Color.GREEN
            tv.backgroundDrawable=context.resources.getDrawable(R.drawable.tv_equip_green)
            holder.itemView.onClick {
                if (isChange){
                    listener.onChange(data.id,position)
                }else{
                    listener.onClick(data.qrcode)
                }
            }
        }else{
            holder.itemView.onClick {
                listener.onAdd(data.id,position)
            }
        }
    }
    
}