package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.CodeEntity
import com.kzj.mall.entity.RegisterEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.RegisterContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class RegisterPresenter @Inject
constructor(model: RegisterContract.Model?, view: RegisterContract.View?, context: Context?)
    : BasePresenter<RegisterContract.Model, RegisterContract.View>(model, view, context) {

    /**
     * 获取注册验证码
     */
    fun requestRegisterCode(mobile: String?) {
        model?.requestRegisterCode(mobile)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CodeEntity>() {
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

    /**
     * 注册
     */
    fun register(mobile: String?, code: String?, password: String?) {
        if (!RegexUtils.isMobileSimple(mobile)) {
            ToastUtils.showShort("手机号码格式错误")
            return
        }

        if (code?.length!! < 6) {
            ToastUtils.showShort("验证码错误")
            return
        }

        if (password?.length!! < 6){
            ToastUtils.showShort("密码长度不能小于6")
            return
        }

        model?.register(mobile, code, password)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<RegisterEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: RegisterEntity?) {
                        view?.registerSuccess(mobile, t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }

    override fun updateCountDown(s: Int) {
        view?.undateCountDownTime(s)
    }

    override fun countDownFinish() {
        view?.countDownFinish()
    }
}