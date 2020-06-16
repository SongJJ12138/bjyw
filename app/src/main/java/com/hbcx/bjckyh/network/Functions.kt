package com.hbcx.bjckyh.network

import android.app.Activity
import com.hbcx.bjckyh.ui.BaseActivity
import com.hbcx.bjckyh.utils.defaultScheduler
import io.reactivex.Flowable

/**
 * Created on 2018/4/12.
 */

inline fun Activity.isBaseActivity(next:(activity: BaseActivity)->Unit){
        if (this is BaseActivity) {
            next(this)
        }
}

inline fun <reified O, I : ResultData<O>> Flowable<I>.request(activity: BaseActivity, showToast: Boolean = true, crossinline success: (msg:String?, t: O?) -> Unit, crossinline error : (code: Int, msg: String) -> Unit) {
    this.defaultScheduler().subscribe(object : ResultDataSubscriber<O>(activity){
        override fun onSuccess(msg: String?, data: O?) {
            success(msg,data)
            activity.dismissDialog()
        }
        override fun isShowToast() = showToast

        override fun onError(code: Int, msg: String) {
            super.onError(code, msg)
            error(code,msg)
            activity.dismissDialog()
        }
    })
}
inline fun <reified O, I : ResultData<O>> Flowable<I>.request(activity: BaseActivity, showToast: Boolean = true, crossinline success: (msg:String?, t: O?) -> Unit) {
    request(activity,showToast,success){_,_->}
}


