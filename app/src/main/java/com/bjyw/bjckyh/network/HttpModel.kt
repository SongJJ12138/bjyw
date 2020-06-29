package com.bjyw.bjckyh.network

import android.content.Context
import android.graphics.Bitmap
import com.bjyw.bjckyh.bean.daobean.InspectEnvironMent
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.ArrayList

class HttpModel(val httpClientListener: HttpClientListener) {
    private fun convertBitmapToFile(context: Context, bitmaps: List<Bitmap>): List<File> {
        val files = ArrayList<File>()
        for (bitmap in bitmaps) {
            try {
                // create a file to write bitmap data
                val f = File(context.cacheDir, "portrait")
                f.createNewFile()
                // convert bitmap to byte array
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
                val bitmapdata = bos.toByteArray()
                // write the bytes in file
                val fos = FileOutputStream(f)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
                files.add(f)
            } catch (e: Exception) {
            }

        }
        return files
    }
    fun postFile(context:Context , list: List<Bitmap>) {
        val files = convertBitmapToFile(context, list)
        val map1 =HashMap<String,File>()
        for (i in files.indices) {
            map1[files[i].name+i+".jpg"] = files[i]
        }
        OkHttpUtils
            .post()
            .url(Api.BASE_URL + Api.UPLOAD_PIC)
            .files("files", map1)
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call, e: Exception, id: Int) {
                    httpClientListener.onError()
                }

                override fun onResponse(response: String, id: Int) {
                    if (!response.contains("FAll")) {
                        httpClientListener.onSuccess(response)
                    } else {
                        httpClientListener.onError()
                    }
                }
            })
    }
    fun postFile2(
        context: Context,
        list: List<File>,
        environ: InspectEnvironMent
    ) {
        val map1 =HashMap<String,File>()
        for (i in list.indices) {
            map1[list[i].name+i+".jpg"] = list[i]
        }
        OkHttpUtils
            .post()
            .url(Api.BASE_URL + Api.UPLOAD_PIC)
            .files("files", map1)
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call, e: Exception, id: Int) {
                    httpClientListener.onError()
                }

                override fun onResponse(response: String, id: Int) {
                    if (!response.contains("FAll")) {
                        httpClientListener.onSuccess2(response,environ)
                    } else {
                        httpClientListener.onError()
                    }
                }
            })
    }
    fun postFile3(
        context: Context,
        list: List<File>
    ) {
        val map1 =HashMap<String,File>()
        for (i in list.indices) {
            map1[list[i].name+i+".jpg"] = list[i]
        }
        OkHttpUtils
            .post()
            .url(Api.BASE_URL + Api.UPLOAD_PIC)
            .files("files", map1)
            .build()
            .execute(object : StringCallback() {
                override fun onError(call: Call, e: Exception, id: Int) {
                    httpClientListener.onError()
                }

                override fun onResponse(response: String, id: Int) {
                    if (!response.contains("FAll")) {
                        httpClientListener.onSuccess(response)
                    } else {
                        httpClientListener.onError()
                    }
                }
            })
    }
    interface HttpClientListener {
        fun onError()
        fun onSuccess(obj: Any)
        fun onSuccess2(response: String, environ: InspectEnvironMent)
    }
}