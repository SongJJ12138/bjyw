package com.bjyw.bjckyh.utils


import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.text.SimpleDateFormat


/**
 * 打印公司日志
 */
fun Any?.sysErr(msg:Any?){
    if (xldUtils.DEBUG)
        Log.e("sinata","--------"+msg)
}
fun <T> Flowable<T>.ioScheduler(): Flowable<T> = this.subscribeOn(Schedulers.io())
fun <T> Flowable<T>.defaultScheduler(): Flowable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun <T> Observable<T>.defaultScheduler(): Observable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun date2String(date:Date):String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return  simpleDateFormat.format(date)
}
fun convertBitmapToFile(context: Context, bitmap: Bitmap): File {
    val f = File(context.cacheDir, "portrait")
        try {
            // create a file to write bitmap data
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
        } catch (e: Exception) {
        }
    return f
}