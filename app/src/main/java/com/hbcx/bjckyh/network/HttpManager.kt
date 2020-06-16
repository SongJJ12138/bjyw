package com.hbcx.bjckyh.network

import android.text.TextUtils
import android.util.Log
import com.google.gson.JsonObject
import com.hbcx.bjckyh.bean.Notify
import com.hbcx.bjckyh.bean.Order
import com.hbcx.bjckyh.bean.Signin
import com.hbcx.bjckyh.bean.user
import com.hbcx.bjckyh.utils.DES
import com.hbcx.bjckyh.utils.SPUtils
import com.hbcx.bjckyh.utils.defaultScheduler
import com.hbcx.bjckyh.utils.getTimeOfWeek
import io.reactivex.Flowable
import org.json.JSONObject

/**
 * 网络请求处理
 */
object HttpManager {

    const val encodeDES = false
    private class ParamsBuilder private constructor() {
        private val sb: StringBuilder = StringBuilder()

        fun build(): String {
            return sb.toString()
        }

        fun build(des: Boolean): String {
            return if (des) {
                Log.d("server:", sb.toString())
                DES.encryptDES(sb.toString())
            } else {
                sb.toString()
            }
        }

        fun append(key: String, value: String): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Int): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Double): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Float): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Long): ParamsBuilder {
            _append(key, value)
            return this
        }

        private fun _append(key: String, value: Any) {
            var value = value
            if (value is String) {

                if ("null" == value || TextUtils.isEmpty(value.toString())) {
                    value = ""
                }
            }
            if (sb.isEmpty()) {
                sb.append(key)
                sb.append(SPLIT)
                sb.append(value)
            } else {
                if (sb.contains(BEGIN)) {
                    sb.append(AND)
                    sb.append(key)
                    sb.append(SPLIT)
                    sb.append(value)
                } else {
                    sb.append(BEGIN)
                    sb.append(key)
                    sb.append(SPLIT)
                    sb.append(value)
                }
            }
        }

        companion object {
            const val SPLIT = "="
            const val AND = "&"
            const val BEGIN = "?"

            fun create(): ParamsBuilder {
                return ParamsBuilder()
            }
        }

    }
    /**
     * 发起请求方法
     */
    private fun request() =
        RRetrofit.instance().create(ApiService::class.java)

    /**
     * 登陆
     */
    fun login(name: String, password: String): Flowable<ResultData<user>> {
        var jsonObject=JSONObject()
        jsonObject.put("loginName",name)
        jsonObject.put("password",password)
        return request().login(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 检查打卡
     */
    fun checkCard(): Flowable<ResultData<ArrayList<Signin>>> {
        var jsonObject=JSONObject()
        jsonObject.put("userId",SPUtils.instance().getInt("userId"))
        return request().checkCard(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 获取次日工单
     */
    fun getOrder(): Flowable<ResultData<ArrayList<Order>>> {
        var jsonObject=JSONObject()
        jsonObject.put("isToday","0")
        jsonObject.put("creator",""+SPUtils.instance().getInt("userId"))
        return request().getOrder(jsonObject.toString()).defaultScheduler()
    }
    /**
     * 获取通知
     */
    fun getNotify(): Flowable<ResultData<ArrayList<Notify>>> {
        var jsonObject=JSONObject()
        jsonObject.put("startTime",getTimeOfWeek(0))
        jsonObject.put("endTime",""+getTimeOfWeek(6))
        jsonObject.put("reciver","4")
//        jsonObject.put("reciver",""+SPUtils.instance().getInt("userId"))
        return request().getNotify(jsonObject.toString()).defaultScheduler()
    }

}

