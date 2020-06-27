package com.bjyw.bjckyh.network

import android.text.TextUtils
import android.util.Log
import com.bjyw.bjckyh.bean.*
import com.bjyw.bjckyh.utils.DES
import com.bjyw.bjckyh.utils.SPUtils
import com.bjyw.bjckyh.utils.defaultScheduler
import io.reactivex.Flowable
import okhttp3.MediaType
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.math.BigInteger
import okhttp3.MultipartBody
import okhttp3.RequestBody



/**
 * 网络请求处理
 */
object HttpManager {

    const val encodeDES = false
    private class ParamsBuilder private constructor() {
        private val sb: StringBuilder = StringBuilder()

        fun build(): String {
            return sb.toString()
        }

        fun build(des: Boolean): String {
            return if (des) {
                Log.d("server:", sb.toString())
                DES.encryptDES(sb.toString())
            } else {
                sb.toString()
            }
        }

        fun append(key: String, value: String): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Int): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Double): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Float): ParamsBuilder {
            _append(key, value)
            return this
        }

        fun append(key: String, value: Long): ParamsBuilder {
            _append(key, value)
            return this
        }

        private fun _append(key: String, value: Any) {
            var value = value
            if (value is String) {

                if ("null" == value || TextUtils.isEmpty(value.toString())) {
                    value = ""
                }
            }
            if (sb.isEmpty()) {
                sb.append(key)
                sb.append(SPLIT)
                sb.append(value)
            } else {
                if (sb.contains(BEGIN)) {
                    sb.append(AND)
                    sb.append(key)
                    sb.append(SPLIT)
                    sb.append(value)
                } else {
                    sb.append(BEGIN)
                    sb.append(key)
                    sb.append(SPLIT)
                    sb.append(value)
                }
            }
        }

        companion object {
            const val SPLIT = "="
            const val AND = "&"
            const val BEGIN = "?"

            fun create(): ParamsBuilder {
                return ParamsBuilder()
            }
        }

    }
    /**
     * 发起请求方法
     */
    private fun request() =
        RRetrofit.instance().create(ApiService::class.java)

    /**
     * 登陆
     */
    fun login(name: String, password: String): Flowable<ResultData<user>> {
        var jsonObject=JSONObject()
        jsonObject.put("loginName",name)
        jsonObject.put("password",password)
        return request().login(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 检查打卡
     */
    fun checkCard(): Flowable<ResultData<ArrayList<Signin>>> {
        var jsonObject=JSONObject()
        jsonObject.put("userId",SPUtils.instance().getInt("userId"))
        return request().checkCard(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 获取次日工单
     */
    fun getOrder(): Flowable<ResultData<ArrayList<Order>>> {
        var jsonObject=JSONObject()
        jsonObject.put("isToday","0")
        jsonObject.put("creator",""+SPUtils.instance().getInt("userId"))
        return request().getOrder(jsonObject.toString()).defaultScheduler()
    }
    /**
     * 获取通知
     */
    fun getNotify(): Flowable<ResultData<ArrayList<Notify>>> {
        var jsonObject=JSONObject()
        jsonObject.put("isRead","0")
        jsonObject.put("reciver",""+SPUtils.instance().getInt("userId"))
        return request().getNotify(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 获取站点信息
     */
    fun getSite(): Flowable<ResultData<ArrayList<Site>>> {
        var jsonObject=JSONObject()
        return request().getSite(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 创建次日工单
     */
    fun createOrder(siteId:Int,planTime:String): Flowable<ResultData<ArrayList<BigInteger>>> {
        var jsonObject=JSONObject()
        jsonObject.put("status","1")
        jsonObject.put("type","1")
        jsonObject.put("creator",""+SPUtils.instance().getInt("userId"))
        var site=JSONArray()
        site.put(siteId)
        jsonObject.put("siteId",site)
        jsonObject.put("planTime",planTime)
        return request().createOrder(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 根据二维码返回站点
     */
    fun getQcode(content:String): Flowable<ResultData<ArrayList<Qcode>>> {
        var jsonObject=JSONObject()
        jsonObject.put("qrcode",content)
        return request().getQcode(jsonObject.toString()).defaultScheduler()
    }

    /**
     * （type为0是巡检条件，1是工作条件）
     */
    fun getCoition(content:Int): Flowable<ResultData<ArrayList<InspectItem>>> {
        var jsonObject=JSONObject()
        jsonObject.put("type",""+content)
        return request().getCoition(jsonObject.toString()).defaultScheduler()
    }


    /**
     * 使用状态
     */
    fun getUseStatus(content:Int): Flowable<ResultData<ArrayList<UseSttus>>> {
        var jsonObject=JSONObject()
        jsonObject.put("conId",""+content)
        return request().getUseStatus(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 全部设备
     */
    fun getAllEquip(siteId:Int): Flowable<ResultData<ArrayList<Equip>>> {
        var jsonObject=JSONObject()
        jsonObject.put("siteId",""+siteId)
        return request().getAllEquip(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 设备巡检项
     */
    fun getEquipUsual(equipId:String): Flowable<ResultData<ArrayList<EquipUsual>>> {
        var jsonObject=JSONObject()
        jsonObject.put("eqId",equipId)
        return request().getEquipUsual(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 上传照片
     */
    fun updataPic(files: ArrayList<File>): Flowable<ResultData<String>> {
        var partList=ArrayList<MultipartBody.Part>()
        for (i in 0 until files.size){
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), files[i])
            val body =
                MultipartBody.Part.createFormData("photo"+i, "photo"+i, requestFile)
            partList.add(body)
        }
        return request().updataPic(partList).defaultScheduler()
    }

    /**
     * 获取维修材料
     */
    fun getConsumable(): Flowable<ResultData<ArrayList<Consumable>>> {
        var jsonObject=JSONObject()
        return request().getConsumable(jsonObject.toString()).defaultScheduler()
    }

    /**
     * 提交工单
     */
    fun commit(jsonject:String): Flowable<ResultData<String>> {
        return request().commit(jsonject).defaultScheduler()
    }


    /**
     * 提交工单
     */
    fun deleteOrder(jsonject:String): Flowable<ResultData<String>> {
        return request().commit(jsonject).defaultScheduler()
    }

}

