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
    var id: Int,
    val name: String,
    var qrcode: String
)

data class Picture(
    var dmj: String,
    var dmy: String,
    var wky : String,
    var tj : String,
    var dzy : String,
    var jq : String,
    var ssj : String,
    var gqq : String,
    var jwd : String,
    var dxj: String
)