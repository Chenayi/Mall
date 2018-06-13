package com.kzj.mall.base

import android.content.Context
import com.kzj.mall.http.HttpUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BasePresenter<M : IModel, V : IView>(model: M, view: V, context: Context, httpUtils: HttpUtils) : IPresenter {

    protected var mModel = model
    protected var mView = view
    protected var mContext = context
    protected var mHttpUtils = httpUtils

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