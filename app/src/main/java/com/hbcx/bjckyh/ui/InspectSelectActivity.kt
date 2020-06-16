package com.hbcx.bjckyh.ui

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import com.hbcx.bjckyh.R
import kotlinx.android.synthetic.main.activity_inspect_select.*
import kotlinx.android.synthetic.main.item_workstatus.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textColor
import com.yzq.zxinglibrary.android.CaptureActivity
import android.content.Intent
import android.app.Activity
import com.yzq.zxinglibrary.common.Constant
import org.jetbrains.anko.toast


class InspectSelectActivity : BaseActivity() {
val REQUEST_CODE_SCAN=0x01
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect_select)
        initView()
        initClick()
    }

    private fun initClick() {
        activity_include_btback.onClick {
            this@InspectSelectActivity.finish()
        }
        activity_include_tvrignt.onClick {

        }
        layout_saoma.onClick {
            val intent = Intent(this@InspectSelectActivity, CaptureActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SCAN)
        }
        layout_power.onClick {
            ObjectAnimator.ofFloat(layout_powercontent, "scaleY", 1F, 0F).apply {
                duration = 1000
                // 设置动画完毕后再倒回去, repeatMode 和 repeatCount 需要都设置才有效果
            }.start()
        }
    }

    private fun initView() {
        activity_include_tvrignt.text="工单列表"
        activity_include_tvrignt.textColor=Color.WHITE
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            if (data != null) {

                val content = data.getStringExtra(Constant.CODED_CONTENT)
                toast("扫描结果为：" + content!!)
            }
        }
    }

}
