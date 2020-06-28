package com.bjyw.bjckyh.network

import com.bjyw.bjckyh.bean.*
import io.reactivex.Flowable
import okhttp3.MultipartBody
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
    fun getAllEquip(@Field("json") json: String): Flowable<ResultData<ArrayList<EquipBean>>>

    @POST(Api.GET_EQUIPUSUAL)
    @FormUrlEncoded
    fun getEquipUsual(@Field("json") json: String): Flowable<ResultData<ArrayList<EquipUsual>>>

    @Multipart
    @POST(Api.UPLOAD_PIC)
    fun updataPic(@Header("watermark")  watermark:Boolean=true, @Part files:List<MultipartBody.Part> ): Flowable<ResultData<String>>

    @POST(Api.GET_CONSUMABLE)
    @FormUrlEncoded
    fun getConsumable(@Field("json") json: String): Flowable<ResultData<ArrayList<Consumable>>>

    @POST(Api.COMMIT)
    @FormUrlEncoded
    fun commit( @Field("json") json: String ): Flowable<ResultData<String>>

    @POST(Api.DELETE_ORDER)
    @FormUrlEncoded
    fun deleteOrder( @Field("json") json: String ): Flowable<ResultData<String>>

    @POST(Api.GET_SITEDETAILS)
    @FormUrlEncoded
    fun getSiteDetails( @Field("json") json: String ): Flowable<ResultData<SiteDetails>>


}