package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MainContract
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject
constructor(model: MainContract.Model, view: MainContract.View?, context: Context?)
    : BasePresenter<MainContract.Model, MainContract.View>(model, view, context) {

    /**
     * 检测更新
     */
    fun checkUpdate() {

    }

}