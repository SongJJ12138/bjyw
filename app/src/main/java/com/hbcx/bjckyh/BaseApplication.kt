package com.hbcx.bjckyh

import android.app.Application
import com.gyf.immersionbar.ImmersionBar
import com.hbcx.bjckyh.utils.xldUtils

/**
 *
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        xldUtils.init(this)
    }

    fun getSPName(): String {
        return "ckyh"
    }


}