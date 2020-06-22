package com.bjyw.bjckyh.network

import com.google.gson.Gson
import com.bjyw.bjckyh.bean.ErrorResponse

import java.io.IOException
import java.lang.reflect.Type

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Converter

class GsonResponseBodyConverter<Any>(private val gson: Gson, private val type: Type) :
    Converter<ResponseBody, Any> {
    @Throws(IOException::class)
    override fun convert(value: ResponseBody): Any? {

        val response = value.string()
        //先将返回的json数据解析到Response中，如果code==200，则解析到我们的实体基类中，否则抛异常
        val httpResult = gson.fromJson(response, Response::class.java)
        if (httpResult.code() == 200) {
            //200的时候就直接解析，不可能出现解析异常。因为我们实体基类中传入的泛型，就是数据成功时候的格式
            return gson.fromJson<Any>(response, type)
        } else {
            val (code, _, message) = gson.fromJson(response, ErrorResponse::class.java)
            //抛一个自定义ResultException 传入失败时候的状态码，和信息
            throw ResultException(code, message)
        }
    }
}