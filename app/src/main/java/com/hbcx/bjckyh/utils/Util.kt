package com.hbcx.bjckyh.utils


import android.content.Context
import android.util.Log
import com.hbcx.bjckyh.network.ResultData
import com.hbcx.bjckyh.ui.BaseActivity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.text.SimpleDateFormat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




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
/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dp2px(context: Context, dpValue: Float): Int {
    return (0.5f + dpValue * context.resources.displayMetrics.density).toInt()
}
fun getTimeOfWeek(i:Int): String {
    val ca = Calendar.getInstance()
    ca.set(Calendar.HOUR_OF_DAY, 0)
    ca.clear(Calendar.MINUTE)
    ca.clear(Calendar.SECOND)
    ca.clear(Calendar.MILLISECOND)
    ca.set(Calendar.DAY_OF_WEEK, ca.firstDayOfWeek+i)
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //设置格式
    val a=ca.timeInMillis
    val timeText = format.format(a)
    return timeText
}
fun date2String(date:Date):String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    return  simpleDateFormat.format(date)
}