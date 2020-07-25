package com.bjyw.bjckyh.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.bjyw.bjckyh.R
import kotlinx.android.synthetic.main.dialog_tishi.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class CommitSuccessDialog  (context: Context, val listener:onClickListener) : Dialog(context) {
    interface onClickListener{
        fun onClick()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_success)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        truee.onClick {
            dismiss()
            listener.onClick()
        }
    }
}