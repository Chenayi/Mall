package com.kzj.mall.base

class BasePresenter<M : IModel, V : IView>(model: M, view: V?) : IPresenter {

    protected var mModel: M = model
    protected var mView: V? = view

    init {
        this.onStart()
    }

    override fun onStart() {

    }

    override fun onDestory() {

    }
}