package com.bjyw.bjckyh.ui

import android.content.Intent
import android.os.Bundle
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.SPUtils
import kotlinx.android.synthetic.main.activity_mine.*
import kotlinx.android.synthetic.main.toolbar_title.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast



class MineActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)
        mine_photo.setBackgroundResource(R.mipmap.logo)
        activity_include_tvtitle.text="个人信息"
        activity_include_tvrignt.text=""
        activity_include_btback.onClick {
            finish()
        }
        if (SPUtils.instance().getInt("userId",0)==0){
            toast("未找到工作人员信息，请先登录")
            return
        }
        getData()
        bt_change.onClick {
            SPUtils.instance().remove("userId").apply()
            var intent=Intent(this@MineActivity,LogonActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this@MineActivity.finish()
        }
    }

    private fun getData() {
        var id=  SPUtils.instance().getInt("userId")
        HttpManager.getMine(""+id).request(this@MineActivity){ _, data->
            data?.let {
                mine_name.text=it.name
                mine_name2.text="姓名：           "+it.name
                mine_phone.text="联系方式：    "+it.phone
            }
        }
    }
}
