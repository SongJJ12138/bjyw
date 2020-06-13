package com.hbcx.bjckyh.ui

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbcx.bjckyh.adapter.NoticeAdapter
import com.hbcx.bjckyh.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_mian_recyclerword.layoutManager= LinearLayoutManager(applicationContext)
        activity_mian_recyclerword.adapter=adapter
    }
    private val adapter by lazy {
        var list= ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        NoticeAdapter(list)
    }
}
