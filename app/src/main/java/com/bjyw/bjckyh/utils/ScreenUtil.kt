package com.bjyw.bjckyh.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import org.jetbrains.anko.displayMetrics
import org.jetbrains.anko.windowManager

/**
 * 屏幕尺寸工具
 */
fun Context.screenWidth():Int{
    val dm = this.displayMetrics
    this.windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Fragment.screenWidth(): Int = activity?.screenWidth()?:0

fun View.screenWidth():Int = context.screenWidth()

fun Context.screenHeight():Int{
    val dm =this. displayMetrics
    this.windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

fun Fragment.screenHeight():Int = context?.screenHeight()?:0

fun View.screenHeight():Int = context.screenHeight()
/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dp2px(context: Context, dpValue: Float): Int {
    return (0.5f + dpValue * context.resources.displayMetrics.density).toInt()
}