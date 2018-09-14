package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.address.AddressEntity
import com.kzj.mall.entity.address.CreateAddressEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MyAddressListContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class MyAddressListPresenter @Inject
constructor(model: MyAddressListContract.Model, view: MyAddressListContract.View?, context: Context?)
    : BasePresenter<MyAddressListContract.Model, MyAddressListContract.View>(model, view, context) {


    fun requestAddress(isShowLoading: Boolean) {
        model?.requestAddress()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AddressEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (isShowLoading) {
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: AddressEntity?) {
                        view?.showAddress(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun updateAddress(infoProvince: String, infoCity: String, infoCounty: String, addressName: String,
                      addressMoblie: String, addressDetail: String, isDefault: String, addressId: String) {
        val params = HashMap<String, String>()
        params.put("infoProvince", infoProvince)
        params.put("infoCity", infoCity)
        params.put("infoCounty", infoCounty)
        params.put("addressName", addressName)
        params.put("addressMoblie", addressMoblie)
        params.put("addressDetail", addressDetail)
        params.put("isDefault", isDefault)
        params.put("addressId", addressId)

        model?.addOrUpdateAddress(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CreateAddressEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: CreateAddressEntity?) {
                        view?.addOrUpdateAddressSuccess(t?.cAddress)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun deleteAddress(addressid: String?) {
        model?.deleteAddress(addressid)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.deleteAddressSuccess()
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