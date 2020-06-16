package com.hbcx.bjckyh.bean

data class Signin(
    val check_time: CheckTime,
    val createTimeFormat: String,
    val create_time: Any,
    val id: Long,
    val index: Int,
    val lat: Double,
    val lng: Double,
    val name: String,
    val sequenceName: String,
    val updateTimeFormat: String,
    val update_time: Any,
    val userId: Int,
    val village_id: Int
)

data class CheckTime(
    val date: Int,
    val day: Int,
    val hours: Int,
    val minutes: Int,
    val month: Int,
    val nanos: Int,
    val seconds: Int,
    val time: Long,
    val timezoneOffset: Int,
    val year: Int
)