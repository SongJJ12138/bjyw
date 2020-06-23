package com.bjyw.bjckyh.network

import com.bjyw.bjckyh.bean.*
import io.reactivex.Flowable
import retrofit2.http.*
import java.math.BigInteger

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

    @POST(Api.GET_SITE)
    @FormUrlEncoded
    fun getSite(@Field("json") json: String): Flowable<ResultData<ArrayList<Site>>>

    @POST(Api.CREATE_ORDER)
    @FormUrlEncoded
    fun createOrder(@Field("json") json: String): Flowable<ResultData<ArrayList<BigInteger>>>

    @POST(Api.SITE_BYQCODE)
    @FormUrlEncoded
    fun getQcode(@Field("json") json: String): Flowable<ResultData<ArrayList<Qcode>>>

    @POST(Api.GET_COITION)
    @FormUrlEncoded
    fun getCoition(@Field("json") json: String): Flowable<ResultData<ArrayList<InspectItem>>>

    @POST(Api.GET_USESTATUS)
    @FormUrlEncoded
    fun getUseStatus(@Field("json") json: String): Flowable<ResultData<ArrayList<UseSttus>>>

    @POST(Api.GET_ALLEQUIP)
    @FormUrlEncoded
    fun getAllEquip(@Field("json") json: String): Flowable<ResultData<ArrayList<Equip>>>

    @POST(Api.GET_EQUIPUSUAL)
    @FormUrlEncoded
    fun getEquipUsual(@Field("json") json: String): Flowable<ResultData<ArrayList<EquipUsual>>>

}