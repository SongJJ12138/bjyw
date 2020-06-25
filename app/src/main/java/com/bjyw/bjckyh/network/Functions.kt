package com.bjyw.bjckyh.network

import android.app.Activity
import com.bjyw.bjckyh.fragment.BaseFragment
import com.bjyw.bjckyh.ui.BaseActivity
import com.bjyw.bjckyh.utils.defaultScheduler
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

inline fun <reified O, I : ResultData<O>> Flowable<I>.requestByF(fragment: BaseFragment, showToast: Boolean = true, crossinline success: (msg:String?, t: O?) -> Unit, crossinline error : (code: Int, msg: String) -> Unit) {
    this.defaultScheduler().subscribe(object : ResultDataSubscriber<O>(fragment){
        override fun onSuccess(msg: String?, data: O?) {
            fragment.dismissDialog()
            success(msg,data)
        }
        override fun isShowToast() = showToast

        override fun onError(code: Int, msg: String) {
            fragment.dismissDialog()
            super.onError(code, msg)
            error(code,msg)
        }
    })
}
inline fun <reified O, I : ResultData<O>> Flowable<I>.requestByF(fragment: BaseFragment, showToast: Boolean = true, crossinline success: (msg:String?, t: O?) -> Unit) {
    requestByF(fragment,showToast,success){_,_->}
}


