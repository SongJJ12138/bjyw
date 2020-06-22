package com.bjyw.bjckyh.network

import com.google.gson.annotations.SerializedName

data class ResultData<T>(@SerializedName("code") var code: Int) {
    @SerializedName("data")
    var data: T? = null
    @SerializedName("message")
    var msg: String? = ""
        get() = field ?: ""
    val sys: Long? = 0
        get() = field ?: 0
}