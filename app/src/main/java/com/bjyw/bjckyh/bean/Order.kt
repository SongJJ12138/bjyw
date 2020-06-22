package com.bjyw.bjckyh.bean

data class Order(
    var createTime: String,
    var detail: Int,
    var districtName: String,
    var endTime: String,
    var grade1: Int,
    var grade2: Int,
    var grade3: Int,
    var grade4: Int,
    var id: String,
    var isLevel: Int,
    var picture: String,
    var planTime: String,
    var qrcode: String,
    var siteId: Int,
    var staffName: String,
    var status: Int,
    var statusName: String,
    var stepName: String,
    var townName: String,
    var type: Int,
    var typeName: String,
    var villageName: String
)