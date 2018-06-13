package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.SplashContract
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject
constructor(model: SplashContract.Model, view: SplashContract.View?, context: Context?)
    : BasePresenter<SplashContract.Model, SplashContract.View>(model, view, context) {

    /**
     * 3秒后跳转到首页
     */
    fun delayJump() {
        LogUtils.e("3秒后跳转到首页...")

    }
}