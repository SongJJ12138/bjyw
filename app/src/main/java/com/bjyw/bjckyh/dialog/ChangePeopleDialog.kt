package com.bjyw.bjckyh.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.bjyw.bjckyh.R
import kotlinx.android.synthetic.main.dialog_changepeople.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ChangePeopleDialog(context: Context,val listener:onClickListener) : Dialog(context) {
    interface onClickListener{
        fun onClick(name:String,phone:String)
        fun onNull()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_changepeople)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        truee.onClick {
            var name=edname.text.toString().trim()
            var phone=edphone.text.toString().trim()
            if (!name.equals("")&&!phone.equals("")){
                dismiss()
                listener.onClick(name,phone)
            }else{
               listener.onNull()
            }
        }
        falsee.onClick {
            dismiss()
        }
    }
}