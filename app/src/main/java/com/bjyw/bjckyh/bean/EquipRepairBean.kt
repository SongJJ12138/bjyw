package com.bjyw.bjckyh.bean

data class EquipRepairBean(
    val consumable: List<Conse>,
    val picture: String,
    val remark: String
)

data class Conse(
    val consumableId: String,
    val count: String,
    val handId: String
)