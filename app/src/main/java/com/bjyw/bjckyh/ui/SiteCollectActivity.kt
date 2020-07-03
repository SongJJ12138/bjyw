package com.bjyw.bjckyh.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.SiteAdapter
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.yzq.zxinglibrary.android.CaptureActivity
import com.yzq.zxinglibrary.common.Constant
import kotlinx.android.synthetic.main.activity_site_collect.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
class SiteCollectActivity : BaseActivity() {
    private val REQUEST_CODE_SCAN=0x01
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
            val intent = Intent(this@SiteCollectActivity, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val content = data.getStringExtra(Constant.CODED_CONTENT)
                getQcode(content!!)
            }
        }
    }
    private fun getQcode(content: String) {
        HttpManager.getQcode(content).request(this@SiteCollectActivity) { _, data ->
            data.let {
                var intent = Intent(this@SiteCollectActivity, SiteUpdateActivity::class.java)
                intent.putExtra("siteId", it!![0].id)
                startActivity(intent)
            }
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
