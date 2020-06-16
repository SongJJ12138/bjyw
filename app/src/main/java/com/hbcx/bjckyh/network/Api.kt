package com.hbcx.bjckyh.network

object Api {
    const val BASE_URL = "http://101.200.167.139:8086/bjyw-controller/" //外网地址
    const val LOGIN = "auth/token" //登陆
    const val CHECK_CARD = "card/isCard" //检查打卡
    const val GET_ORDER = "ticket/getOrder" //获取工单
    const val GET_NOTIFY = "annunciate/getByMap" //获取通知
}