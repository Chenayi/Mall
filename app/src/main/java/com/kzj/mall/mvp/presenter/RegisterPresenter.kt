package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.RegisterCodeEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.RegisterContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class RegisterPresenter @Inject
constructor(model: RegisterContract.Model?, view: RegisterContract.View?, context: Context?)
    : BasePresenter<RegisterContract.Model, RegisterContract.View>(model, view, context) {
    fun requestRegisterCode(mobile: String?) {
        model?.requestRegisterCode(mobile)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<RegisterCodeEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: RegisterCodeEntity?) {
                        view?.sendCodeSuccess()
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.sendCodeError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }
}