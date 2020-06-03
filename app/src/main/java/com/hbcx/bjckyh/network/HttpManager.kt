package com.hbcx.bjckyh.network

import android.text.TextUtils
import android.util.Log
import com.google.gson.JsonObject
import com.hbcx.bjckyh.utils.DES
import com.hbcx.bjckyh.utils.defaultScheduler
import io.reactivex.Flowable

/**
 * 网络请求处理
 */
object HttpManager {

    const val PAGE_SIZE = 20
    const val encodeDES = true
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
    private fun request() = RRetrofit.instance().create(ApiService::class.java)


    /**
     * 退票
     */
    fun refundTicket(id: Int, ridingIds: String): Flowable<ResultData<JsonObject>> {
        val request = ParamsBuilder.create().append("server", Api.TICKET_REFUND)
                .append("id", id).append("ridingIds", ridingIds)
        return request().simpleRequest(request.build(encodeDES)).defaultScheduler()
    }


    /**
     * 邀请记录
     */
    fun getInviteRecord(userId: Int, page: Int): Flowable<ResultData<ArrayList<String>>> {
        val request = ParamsBuilder.create().append("server", Api.INVITE_RECORD)
                .append("userId", userId).append("page", page).append("rows", PAGE_SIZE)
        return request().getInviteRecord(request.build(encodeDES)).defaultScheduler()
    }
    /**
     * 到达站点
     */
    fun getEndStation(cityCode: String,lineType:Int,stationId:Int): Flowable<ResultData<ArrayList<String>>> {
        val request = ParamsBuilder.create().append("server", Api.END_STATION).append("cityCode", cityCode)
                .append("lineType",lineType).append("stationId",stationId)
        return request().getStaions(request.build(encodeDES)).defaultScheduler()
    }

}

