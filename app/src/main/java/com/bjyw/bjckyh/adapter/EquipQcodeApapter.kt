package com.bjyw.bjckyh.adapter

import android.graphics.Color
import android.widget.TextView
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Equip
import com.bjyw.bjckyh.dialog.EquipQcodeDialog
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor

class EquipQcodeApapter(mData:ArrayList<Equip>, private val listener: onClickListener):HFRecyclerAdapter<Equip>(mData,
    R.layout.item_equip){
    interface onClickListener{
        fun onClick(equipQcode:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Equip) {
        var tv=holder.bind<TextView>(R.id.item_tv_equip)
        tv.text=data.name
        if (!data.qrcode.equals("")){
            tv.textColor=Color.GREEN
            tv.backgroundDrawable=context.resources.getDrawable(R.drawable.tv_equip_green)
            holder.itemView.onClick {
                listener.onClick(data.qrcode)
            }
        }

    }
}