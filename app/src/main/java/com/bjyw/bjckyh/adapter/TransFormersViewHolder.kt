package com.bjyw.bjckyh.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Nav
import com.zaaach.transformerslayout.holder.Holder


class TransFormersViewHolder internal constructor(@NonNull itemView: View) : Holder<Nav>(itemView) {
private var icon: ImageView? = null
private var text: TextView? = null
    private var tishi:TextView?=null
    override fun onBind(context: Context?, list: MutableList<Nav>?, data: Nav?, position: Int) {
        if (data?.isTishi!!){
            tishi?.visibility=View.VISIBLE
            tishi?.text=data.tishi
        }else{
            tishi?.visibility=View.GONE
        }
        text!!.setText(data?.name)
        context?.let {
            Glide.with(it)
                .asBitmap()
                .load(data?.resouse)
                .into(icon!!)
        }
    }

    override fun initView(itemView: View?) {
        icon = itemView?.findViewById(R.id.trans_img)
        text = itemView?.findViewById(R.id.trans_text)
        tishi=itemView?.findViewById(R.id.tv_tishi)
    }

}