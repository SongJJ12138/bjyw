package com.hbcx.bjckyh.adapter

import com.hbcx.bjckyh.R

class NoticeAdapter(mData:ArrayList<String>): HFRecyclerAdapter<String>(mData,
    R.layout.item_notice
) {
    override fun onBind(holder: ViewHolder, position: Int, data: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}