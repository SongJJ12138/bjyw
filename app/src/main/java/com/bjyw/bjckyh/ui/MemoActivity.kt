package com.bjyw.bjckyh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.adapter.MemoAdapter
import com.bjyw.bjckyh.bean.Memo
import kotlinx.android.synthetic.main.activity_memo.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        ininView()
        initClick()
    }

    private fun initClick() {
        activity_include_btback.onClick {
            finish()
        }
        bt_create.onClick {
            var intent=Intent(this@MemoActivity,MemoCreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun ininView() {
        activity_include_tvtitle.text="本地备忘录"
        activity_include_tvrignt.text=""
        rv_memo.layoutManager=LinearLayoutManager(applicationContext)
        rv_memo.adapter=adapter
    }

    val adapter by lazy{
        var list=ArrayList<Memo>()
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        list.add(Memo())
        MemoAdapter(list,object :MemoAdapter.onClickListener{
            override fun onClick(equipQcode: String) {
                var intent=Intent(this@MemoActivity,MemoDetailsActivity::class.java)
                startActivity(intent)
            }
        })
    }
}
