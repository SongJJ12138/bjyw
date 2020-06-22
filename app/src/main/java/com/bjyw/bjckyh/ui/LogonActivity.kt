package com.bjyw.bjckyh.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.SPUtils
import kotlinx.android.synthetic.main.activity_logon.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.regex.Pattern


@Suppress("UNREACHABLE_CODE")
class LogonActivity : BaseActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logon)
        activity_login_edpassword.addTextChangedListener(this)
        activity_login_btlogin.onClick {
            var name=activity_login_edname.text.toString().trim()
            var password=activity_login_edpassword.text.toString().trim()
            if (name.equals("")){
                toast("用户名不能为空！")
                return@onClick
            }
            if (password.equals("")){
                toast("密码不能为空！")
                return@onClick
            }
            showDialog()
            HttpManager.login(name,password).request(this@LogonActivity) { _, data ->
                data?.let {
                    var intent=Intent(this@LogonActivity,MainActivity::class.java)
                    SPUtils.instance().put("userId",it.id).apply()
                    SPUtils.instance().put("name",it.name).apply()
                    SPUtils.instance().put("loginName",it.loginName).apply()
                    SPUtils.instance().put("salt",it.salt).apply()
                    SPUtils.instance().put("token",it.token).apply()
                    startActivity(intent)
                }
            }
        }
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (checknum(s.toString())){
            login_passworderror.visibility= View.VISIBLE
            activity_view_password.backgroundColor=Color.RED
        }else{
            login_passworderror.visibility= View.GONE
            activity_view_password.backgroundColor=applicationContext.resources.getColor(R.color.dimgray)
        }
    }

    override fun afterTextChanged(s: Editable?) {
       
    }
    fun checknum(str:String): Boolean {
        val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(str).matches()
    }

}
