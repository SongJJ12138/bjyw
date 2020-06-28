package com.bjyw.bjckyh.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.bjyw.bjckyh.R
import kotlinx.android.synthetic.main.dialog_qcode.*

class EquipQcodeDialog (context: Context,val qcode:String) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_qcode)
        val window = window
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        tv_qcode.text=qcode
    }
}