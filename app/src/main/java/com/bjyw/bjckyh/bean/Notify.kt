package com.bjyw.bjckyh.bean

class Notify(
    val context: String,
    val createTimeFormat: String,
    val create_time: Any,
    val id: Int,
    val index: Int,
    val isRead: Int,
    val issue: String,
    val reciver: String,
    val recordTimeFormat: String,
    val record_time: RecordTime?,
    val sequenceName: String,
    val status: Int,
    val title: String,
    val type: String,
    val updateTimeFormat: String,
    val update_time: Any
)

data class RecordTime(
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