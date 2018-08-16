package com.kzj.mall.mvp.presenter

import android.content.Context
import android.telephony.PhoneNumberUtils
import android.view.Gravity
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.CodeEntity
import com.kzj.mall.entity.LoginEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.LoginContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject
constructor(model: LoginContract.Model?, view: LoginContract.View?, context: Context?)
    : BasePresenter<LoginContract.Model, LoginContract.View>(model, view, context) {


    fun requestCode(mobile: String?){
        model?.requestCode(mobile)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<CodeEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: CodeEntity?) {
                        view?.sendCodeSuccess()
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.sendCodeError(code, msg)
                    }

                    override fun onHandleAfter() {
                    }

                })
    }

    override fun updateCountDown(s: Int) {
        view?.undateCountDownTime(s)
    }

    override fun countDownFinish() {
        view?.countDownFinish()
    }

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
        if (!connected) {
            ToastUtils.showShort("网络请求失败，请检查您的网络设置")
            return
        }


        model?.loginByCode(mobile, code)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<LoginEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: LoginEntity?) {
                        view?.loginSuccess(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun loginByPassword(username: String?, password: String?) {
        val connected = NetworkUtils.isConnected()
        if (!connected) {
            ToastUtils.showShort("网络请求失败，请检查您的网络设置")
            return
        }

        model?.loginByPassword(username,password)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<LoginEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: LoginEntity?) {
                        view?.loginSuccess(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }

}