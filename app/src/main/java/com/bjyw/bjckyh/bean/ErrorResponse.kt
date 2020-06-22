package com.bjyw.bjckyh.bean

data class ErrorResponse(
    val code: Int,
    val `data`: String,
    val message: String,
    val result: String
)