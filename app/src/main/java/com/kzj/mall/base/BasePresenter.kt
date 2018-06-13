package com.kzj.mall.base

import android.content.Context
import com.kzj.mall.http.HttpUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<M : IModel, V : IView>(val model: M?, val view: V?, val context: Context?) : IPresenter {
    private var mCompositeDisposable: CompositeDisposable? = null

    init {
        this.onStart()
    }

    override fun onStart() {
        mCompositeDisposable = CompositeDisposable()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
    }

    fun disposable() {
        mCompositeDisposable?.clear()
    }

    override fun onDestory() {
        disposable()
    }
}