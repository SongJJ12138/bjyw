package com.bjyw.bjckyh.network

object Api {
    const val BASE_URL = "http://101.200.167.139:8085/bjyw-controller/" //外网地址
    const val LOGIN = "auth/token" //登陆
    const val CHECK_CARD = "card/isCard" //检查打卡
    const val GET_ORDER = "ticket/getOrder" //获取工单
    const val GET_NOTIFY = "annunciate/getByMap" //获取通知
    const val GET_SITE="regoin/getAll"//获取站点信息
    const val CREATE_ORDER="ticket/createOrder"//次日工单创建
    const val SITE_BYQCODE="site/getByMap"//根据二维码返回站点数据
    const val GET_COITION="coition/getByMap"//巡检条件与工作条件
    const val GET_USESTATUS="usual/environment"//使用状态
    const val GET_ALLEQUIP="equip/getByMap"//全部设备
    const val GET_EQUIPUSUAL="usual/equipment"//设备巡检项
    const val UPLOAD_PIC="file/upload"//上传照片
    const val GET_CONSUMABLE="consumable/getAll"//维修项
    const val COMMIT="ticket/excuteOrder"//提交工单
    const val DELETE_ORDER="ticket/delete"//删除工单
    const val GET_SITEDETAILS="site/getSiteDetail"//工单详情
    const val UPDATE_SITE="site/updateSiteMsg"//更新台站
    const val GET_MINE="users/id"//更新台站


}