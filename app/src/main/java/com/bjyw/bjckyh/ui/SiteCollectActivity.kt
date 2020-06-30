package com.bjyw.bjckyh.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.SiteAdapter
import kotlinx.android.synthetic.main.activity_site_collect.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SiteCollectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_collect)
        collectsite_rv_updatesite.layoutManager= GridLayoutManager(applicationContext,3)
        collectsite_rv_updatesite.adapter=updateAdapter
        collectsite_rv_collectsite.layoutManager= GridLayoutManager(applicationContext,3)
        collectsite_rv_collectsite.adapter=collectAdapter
        activity_include_tvtitle.text="台站采集"
        activity_include_tvrignt.text=""
        activity_include_btback.onClick {
            finish()
        }
        tv_caiji.onClick{
            startActivity(Intent(this@SiteCollectActivity,SiteUpdateActivity::class.java))
        }
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
