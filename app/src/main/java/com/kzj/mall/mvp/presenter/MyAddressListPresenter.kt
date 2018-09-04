package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.AddressEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MyAddressListContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class MyAddressListPresenter @Inject
constructor(model: MyAddressListContract.Model, view: MyAddressListContract.View?, context: Context?)
    : BasePresenter<MyAddressListContract.Model, MyAddressListContract.View>(model, view, context) {


    fun requestAddress(){
        model?.requestAddress()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<AddressEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: AddressEntity?) {
                        view?.showAddress(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

}