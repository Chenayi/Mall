package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.CustomerEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.PersonInfoContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class PersonInfoPresenter @Inject
constructor(model: PersonInfoContract.Model, view: PersonInfoContract.View?, context: Context?)
    : BasePresenter<PersonInfoContract.Model, PersonInfoContract.View>(model, view, context) {
    fun customerInfo() {
        model?.customerInfo()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CustomerEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: CustomerEntity?) {
                        view?.showPersonInfo(t?.custAllInfo)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun updateInfo() {

    }
}