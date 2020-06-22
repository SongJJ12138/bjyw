package com.bjyw.bjckyh.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences 工具,链式调用.
 */
class SPUtils() {
    private var sp: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    init {
        sp = xldUtils.context?.getSharedPreferences("bjckyh", Context.MODE_PRIVATE)
        editor = sp!!.edit()
    }

    fun getString(key: String,defValue:String=""): String? = sp!!.getString(key, defValue)

    fun getInt(key: String, defValue: Int=-1):Int = sp!!.getInt(key,defValue)

    fun getFloat(key: String,defValue: Float=0f):Float = sp!!.getFloat(key,defValue)

    fun getLong(key: String,defValue: Long=0):Long = sp!!.getLong(key,defValue)

    fun getBoolean(key: String,defValue: Boolean=false):Boolean = sp!!.getBoolean(key,defValue)

    fun put(key: String, value: Int): SPUtils {
        editor?.putInt(key, value)
        return this
    }

    fun put(key: String, value: Float): SPUtils {
        editor?.putFloat(key, value)
        return this
    }

    fun put(key: String, value: Boolean): SPUtils {
        editor?.putBoolean(key, value)
        return this
    }

    fun put(key: String, value: Long): SPUtils {
        editor?.putLong(key, value)
        return this
    }

    fun put(key: String, value: String?): SPUtils {
        if (value == null) {
            return this
        }
        editor?.putString(key, value)
        return this
    }

    fun remove(key: String):SPUtils {
        editor?.remove(key)
        return this
    }

    fun clear():SPUtils {
        editor?.clear()
        return this
    }

    @Synchronized fun apply() {
        editor?.apply()
    }

    companion object {
        fun instance() : SPUtils = SPUtils()
    }
}
