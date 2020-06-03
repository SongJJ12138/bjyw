package com.hbcx.bjckyh.zInterface

import io.reactivex.disposables.Disposable

interface RequestHelper {
    fun onBindHelper(disposable: Disposable)
    fun onRequestFinish()
    fun errorToast(msg: String?)
}