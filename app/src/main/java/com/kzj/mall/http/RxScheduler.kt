package com.kzj.mall.http

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxScheduler {
    companion object {
        fun <T> compose(): ObservableTransformer<T, T> {
            return ObservableTransformer { observable ->
                observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}