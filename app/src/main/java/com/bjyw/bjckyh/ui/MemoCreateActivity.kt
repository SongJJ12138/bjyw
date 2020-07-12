package com.bjyw.bjckyh.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bjyw.bjckyh.R
import kotlinx.android.synthetic.main.activity_memo_create.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import org.jetbrains.anko.toast

class MemoCreateActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_create)
        initView()
        initClick()
    }

    private fun initClick() {
        activity_include_btback.onClick {
            finish()
        }
        ed_memo_title.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                var number= s?.length
                if (number != null) {
                    if (number>16){
                        tv_memo_size.textColor=Color.RED
                    }else{
                        tv_memo_size.textColor=Color.BLACK
                        tv_memo_size.text="""${number}/16"""
                    }
                }
            }
        })
        activity_include_tvrignt.onClick {
            var title=ed_memo_title.text.toString().trim()
            var content=ed_memo_content.text.toString().trim()
            if (title.equals("")){
                toast("请输入标题")
                return@onClick
            }
            if (content.equals("")){
                toast("请输入内容")
                return@onClick
            }
        }
    }

    private fun initView() {
        activity_include_tvtitle.text="编辑备忘录"
        activity_include_tvrignt.text="保存"
        activity_include_tvrignt.textColor=Color.WHITE
    }
}
