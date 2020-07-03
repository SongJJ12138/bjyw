package com.bjyw.bjckyh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.utils.SPUtils
import kotlinx.android.synthetic.main.activity_mine.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class MineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)
        mine_photo.setBackgroundResource(R.mipmap.logo)
        if (SPUtils.instance().getInt("userId",0)==0){
            toast("未找到工作人员信息，请先登录")
            return
        }
        mine_name.text=SPUtils.instance().getString("name")
        mine_name2.text="姓名：             "+SPUtils.instance().getString("name")
        mine_phone.text="联系方式：    "+SPUtils.instance().getString("token")
        bt_change.onClick {
            SPUtils.instance().clear()
            var intent=Intent(this@MineActivity,LogonActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this@MineActivity.finish()
        }
    }
}
