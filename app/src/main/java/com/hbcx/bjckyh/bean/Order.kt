package com.hbcx.bjckyh.bean

data class Order(
    val createTime: String,
    val detail: Int,
    val districtName: String,
    val endTime: String,
    val grade1: Int,
    val grade2: Int,
    val grade3: Int,
    val grade4: Int,
    val id: String,
    val isLevel: Int,
    val picture: String,
    val planTime: String,
    val qrcode: String,
    val siteId: Int,
    val staffName: String,
    val status: Int,
    val statusName: String,
    val stepName: String,
    val townName: String,
    val type: Int,
    val typeName: String,
    val villageName: String
)