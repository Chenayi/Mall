package com.kzj.mall.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class BaseObserver<T> : Observer<BaseObserver<T>> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: BaseObserver<T>) {

    }

    override fun onError(e: Throwable) {

    }
}