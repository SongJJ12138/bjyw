package com.hbcx.bjckyh.network

import com.google.gson.annotations.SerializedName

data class ResultData<T>(@SerializedName("code") var code: Int) {
    var data: T? = null
    @SerializedName("msg")
    var msg: String? = ""
        get() = field ?: ""
    val sys: Long? = 0
        get() = field ?: 0
}