package com.hbcx.bjckyh.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hbcx.bjckyh.R
import com.hbcx.bjckyh.adapter.WorkStatusAdapter
import kotlinx.android.synthetic.main.activity_inspect_select.*

class InspectSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_select)
        rv_workstatus.layoutManager= LinearLayoutManager(applicationContext)
        rv_workstatus.adapter=adapter
    }
    private val adapter by lazy {
        var list= ArrayList<String>()
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        list.add("a")
        WorkStatusAdapter(list)
    }
}
