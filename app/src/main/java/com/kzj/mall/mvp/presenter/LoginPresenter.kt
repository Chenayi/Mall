package com.kzj.mall.mvp.presenter

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.view.Gravity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.LoginContract
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject
constructor(model: LoginContract.Model?, view: LoginContract.View?, context: Context?)
    : BasePresenter<LoginContract.Model, LoginContract.View>(model, view, context) {

    fun loginByCode(mobile: String?, code: String?) {
        val isMobile = RegexUtils.isMobileSimple(mobile)
        if (!isMobile) {
            ToastUtils.showShort("手机号码格式错误")
            return
        }

        if (code?.length!! < 6) {
            ToastUtils.showShort("验证码错误")
            return
        }

        val connected = NetworkUtils.isConnected()
        if (!connected){
            ToastUtils.showShort("网络请求失败，请检查您的网络设置")
            return
        }

        LogUtils.e("验证码登录...")
    }

    fun loginByPassword(mobile: String?, password: String?){
        val isMobile = RegexUtils.isMobileSimple(mobile)
        if (!isMobile) {
            ToastUtils.showShort("手机号码格式错误")
            return
        }

        val connected = NetworkUtils.isConnected()
        if (!connected){
            ToastUtils.showShort("网络请求失败，请检查您的网络设置")
            return
        }

        LogUtils.e("密码登录...")
    }

}