package com.bjyw.bjckyh.bean

data class user(
    val createdAt: Any,
    val creatorId: Int,
    val creatorName: String,
    val id: Int,
    val locked: Int,
    val loginName: String,
    val name: String,
    val password: String,
    val roleIds: List<Any>,
    val salt: String,
    val token: String
)