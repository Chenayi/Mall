package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.address.CityEntity
import com.kzj.mall.entity.address.DistrictEntity
import com.kzj.mall.entity.address.ProvinceEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.AddressListContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class AddressListPresenter @Inject
constructor(model: AddressListContract.Model?, view: AddressListContract.View?, context: Context?)
    : BasePresenter<AddressListContract.Model, AddressListContract.View>(model, view, context) {

    //省
    fun requestP() {
        model?.requestP()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<ProvinceEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: ProvinceEntity?) {
                       view?.showP(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    //市
    fun requestC(pid:String) {
        model?.requestC(pid)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<CityEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: CityEntity?) {
                        view?.showC(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }

    //区
    fun requestD(cid:String) {
        model?.requestD(cid)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<DistrictEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: DistrictEntity?) {
                        view?.showD(t)
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