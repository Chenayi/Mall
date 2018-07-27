package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.LoginContract
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject
constructor(model: LoginContract.Model?, view: LoginContract.View?, context: Context?)
    : BasePresenter<LoginContract.Model, LoginContract.View>(model, view, context) {

    fun login() {
        LogUtils.e("登录...")
    }

}