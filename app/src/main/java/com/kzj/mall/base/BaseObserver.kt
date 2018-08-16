package com.kzj.mall.base

import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.exception.ResultException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<BaseResponse<T>> {
    override fun onComplete() {
        onHandleAfter()
    }

    override abstract fun onSubscribe(d: Disposable)

    override fun onNext(t: BaseResponse<T>) {
        onHandleSuccess(t.data)
    }

    override fun onError(e: Throwable) {
        if (e is ResultException){
            onHandleError(e?.errorCode,e?.errorMsg)
        }else{
            onHandleError(-1,e?.message)
        }
        onHandleAfter()
    }

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract fun onHandleSuccess(t: T?)

    /**
     * 失败回调
     *
     * @param code
     * @param msg
     */
    protected abstract fun onHandleError(code: Int, msg: String?)

    /**
     * 成功或失败处理完后的回调
     */
    protected abstract fun onHandleAfter()
}