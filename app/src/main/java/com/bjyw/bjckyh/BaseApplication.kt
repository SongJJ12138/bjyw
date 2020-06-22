package com.bjyw.bjckyh

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.baidu.mapapi.SDKInitializer
import com.bjyw.bjckyh.utils.MapLocationUtil
import com.bjyw.bjckyh.utils.xldUtils

/**
 *
 */
class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        xldUtils.init(this)
        SDKInitializer.initialize(applicationContext)
        MapLocationUtil.instance.requestLocation(applicationContext)
    }

    fun getSPName(): String {
        return "ckyh"
    }


}