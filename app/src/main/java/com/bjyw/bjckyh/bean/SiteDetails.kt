package com.bjyw.bjckyh.bean
 data class SiteDetails(
    val contact: String,
    val contactnum: String,
    val district: String,
    val equipList: List<Equip>,
    val frequency: String,
    val grade1: Int,
    val grade2: Int,
    val grade3: Int,
    val grade4: Int,
    val id: Int,
    val lat: Double,
    val lng: Double,
    val picture: Picture,
    val time: String,
    val town: String,
    val village: String
)

data class Equip(
    val id: Int,
    val name: String,
    val qrcode: String
)

data class Picture(
    val dmj: String,
    val dmy: String,
    val dxj: String,
    val dzy: String,
    val ghq: String,
    val gqq: String,
    val jq: String,
    val ssj: String,
    val tj: String,
    val wky: String
)