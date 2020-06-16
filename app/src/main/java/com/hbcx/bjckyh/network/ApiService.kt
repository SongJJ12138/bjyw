package com.hbcx.bjckyh.network

import com.google.gson.JsonObject
import com.hbcx.bjckyh.bean.Notify
import com.hbcx.bjckyh.bean.Order
import com.hbcx.bjckyh.bean.Signin
import com.hbcx.bjckyh.bean.user
import io.reactivex.Flowable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 *
 */
interface ApiService {
    @POST(Api.LOGIN)
    @FormUrlEncoded
    fun login(@Field("json") json:String):Flowable<ResultData<user>>
    
    @POST(Api.CHECK_CARD)
    @FormUrlEncoded
    fun checkCard(@Field("json") json: String):Flowable<ResultData<ArrayList<Signin>>>

    @POST(Api.GET_ORDER)
    @FormUrlEncoded
    fun getOrder(@Field("json") json: String): Flowable<ResultData<ArrayList<Order>>>


    @POST(Api.GET_NOTIFY)
    @FormUrlEncoded
    fun getNotify(@Field("json") json: String): Flowable<ResultData<ArrayList<Notify>>>
}