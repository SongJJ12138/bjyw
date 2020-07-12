package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Memo

class MemoAdapter (mData:ArrayList<Memo>, private val listener: onClickListener):HFRecyclerAdapter<Memo>(mData,
    R.layout.item_memo){
    interface onClickListener{
        fun onClick(equipQcode:String)
    }
    override fun onBind(holder: ViewHolder, position: Int, data: Memo) {

    }

}