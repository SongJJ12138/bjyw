package com.hbcx.bjckyh.network

import com.google.gson.JsonObject
import io.reactivex.Flowable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 *
 */
interface ApiService {

    @POST("app/server")
    fun stringRequest(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun simpleRequest(@Query("key") key: String): Flowable<ResultData<JsonObject>>

    @POST("app/server")
    fun getBanner(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getNearbyDriver(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getPayInfo(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getFastList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getRentList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>
    @GET("app/server")
    fun getCustomLines(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>
    @POST("app/server")
    fun cancelCustomr(@Query("key") key: String):Flowable<ResultData<String>>
    @POST("app/server")
    fun refundCustomr(@Query("key") key: String):Flowable<ResultData<String>>
    @POST("app/server")
    fun sendSms(@Query("phone") server: String, @Query("phone") phone: String, @Query("type") type: Int): Flowable<ResultData<JsonObject>>

    @POST("app/server")
    fun get(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getMain(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getCarList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getGroupCarList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getCarLabels(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getCarBrands(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getCarDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getGroupCarDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getCarSafe(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun companyInfo(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun rentDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun Detail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun companyPoint(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getDriver(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getCoupons(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getOpenCity(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getEndCity(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>


    @POST("app/server")
    fun getMessages(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun lineTypeList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun lineList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun ticketLineList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getLineDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getSpecialLineDetail(@Query("key") key: String): Flowable<ResultData<String>>

    @POST("app/server")
    fun getIntegral(@Query("key") key: String): Flowable<ResultData<String>>

    /**文件上传**/
    @Multipart
    @POST("app/public/uplaodImg")
    fun uploadFile(@Part() filePart: MultipartBody.Part): Flowable<ResultData<JsonObject>>

    @POST("app/server")
    fun getPassengerList(@Query("key") key: String): Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getTicketDetail(@Query("key") key:String):Flowable<ResultData<String>>

    @POST("app/server")
    fun getTicketList(@Query("key") key:String):Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getInviteRecord(@Query("key") key:String):Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getStaions(@Query("key") key:String):Flowable<ResultData<ArrayList<String>>>

    @POST("app/server")
    fun getStations(@Query("key") key:String):Flowable<ResultData<ArrayList<String>>>

    @GET
    fun checkIdCard(@Url url:String, @Query("key") key: String, @Query("idcard") idcard: String, @Query("realname") realname: String) :Flowable<JsonObject>
}