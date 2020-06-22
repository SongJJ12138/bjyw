package com.bjyw.bjckyh.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.SiteAdapter
import kotlinx.android.synthetic.main.activity_site_collect.*

class SiteCollectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_collect)
        collectsite_rv_updatesite.layoutManager= GridLayoutManager(applicationContext,3)
        collectsite_rv_updatesite.adapter=updateAdapter
        collectsite_rv_collectsite.layoutManager= GridLayoutManager(applicationContext,3)
        collectsite_rv_collectsite.adapter=collectAdapter
    }
    private val updateAdapter by lazy {
        var list= ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        SiteAdapter(list)
    }
    private val collectAdapter by lazy {
        var list= ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        SiteAdapter(list)
    }
}
