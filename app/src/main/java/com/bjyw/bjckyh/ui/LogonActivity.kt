package com.bjyw.bjckyh.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bjyw.bjckyh.R
import com.bjyw.bjckyh.network.HttpManager
import com.bjyw.bjckyh.network.request
import com.bjyw.bjckyh.utils.SPUtils
import kotlinx.android.synthetic.main.activity_logon.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.ArrayList
import java.util.regex.Pattern




@Suppress("UNREACHABLE_CODE")
class LogonActivity : BaseActivity(), TextWatcher {
    private val BAIDU_READ_PHONE_STATE = 100
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logon)
        requestPermission()
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

    private fun requestPermission() {
        val permissionList = ArrayList<String>()
        for (permission in permissions) {
            //权限没有授权
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(permission)
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionList.toTypedArray(),
                BAIDU_READ_PHONE_STATE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            BAIDU_READ_PHONE_STATE -> if (grantResults.size > 0) {
                val deniedPermissions = ArrayList<String>()
                for (i in grantResults.indices) {
                    val result = grantResults[i]
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        val permission = permissions[i]
                        deniedPermissions.add(permission)
                    }
                }
                if (!deniedPermissions.isEmpty()) {
                    Toast.makeText(applicationContext, "权限未通过，请重新获取！", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
            }
            else -> {
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
       if (activity_login_edpassword.text.toString().equals("")){
           login_passworderror.visibility= View.GONE
           activity_view_password.backgroundColor=applicationContext.resources.getColor(R.color.dimgray)
       }
    }
    fun checknum(str:String): Boolean {
        val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
        return pattern.matcher(str).matches()
    }

}
