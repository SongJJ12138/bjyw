package com.bjyw.bjckyh.adapter

import com.bjyw.bjckyh.R

class SiteAdapter (mData:ArrayList<String>):HFRecyclerAdapter<String>(mData, R.layout.item_site)  {
    override fun onBind(holder: ViewHolder, position: Int, data: String) {
    }
}