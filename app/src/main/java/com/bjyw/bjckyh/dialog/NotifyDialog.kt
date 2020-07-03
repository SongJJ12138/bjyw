package com.bjyw.bjckyh.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.bean.Notify
import kotlinx.android.synthetic.main.dialog_notify.*
import android.graphics.drawable.ColorDrawable



class NotifyDialog(context: Context, private var mMessage: Notify) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_notify)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        title.text=mMessage.title
        content.text="  "+mMessage.context
        time.text="发布日期："+mMessage.recordTimeFormat
    }
}