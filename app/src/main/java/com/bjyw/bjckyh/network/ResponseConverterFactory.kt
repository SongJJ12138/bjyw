package com.bjyw.bjckyh.network

import com.google.gson.Gson
import java.lang.reflect.Type

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class ResponseConverterFactory private constructor(private val gson: Gson?) : Converter.Factory() {

    init {
        if (gson == null) throw NullPointerException("gson == null")
    }

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        //返回我们自定义的Gson响应体变换器
        return GsonResponseBodyConverter<Any>(gson!!, type!!)
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>?,
        methodAnnotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        //返回我们自定义的Gson响应体变换器
        return GsonResponseBodyConverter(gson!!, type!!)
    }

    companion object {


        @JvmOverloads
        fun create(gson: Gson = Gson()): ResponseConverterFactory {
            return ResponseConverterFactory(gson)
        }
    }
}
