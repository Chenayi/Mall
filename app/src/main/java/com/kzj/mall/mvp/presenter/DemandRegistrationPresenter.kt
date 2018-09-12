package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.DemandRegistrationContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class DemandRegistrationPresenter @Inject
constructor(model: DemandRegistrationContract.Model, view: DemandRegistrationContract.View?, context: Context?)
    : BasePresenter<DemandRegistrationContract.Model, DemandRegistrationContract.View>(model, view, context) {

    fun submitDemand(rxRecordType: String?, consignee: String?, mobile: String?, goodsinfo: String?, goodsinfoid: String?
                     , address: String?, quantity: String?, message: String?, rxImage: String?, addressId: String?) {

        val params = HashMap<String, String>()
        rxRecordType?.let {
            params.put("rxRecordType", it)
        }

        consignee?.let {
            params.put("consignee", it)
        }

        mobile?.let {
            params.put("mobile", it)
        }

        goodsinfo?.let {
            params.put("goodsinfo", it)
        }

        goodsinfoid?.let {
            params.put("goodsinfoid", it)
        }

        address?.let {
            params.put("address", it)
        }

        quantity?.let {
            params.put("quantity", it)
        }

        message?.let {
            params.put("message", it)
        }

        rxImage?.let {
            params.put("rxImage", it)
        }

        addressId?.let {
            params.put("addressId", it)
        }


        model?.demand(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.submitDemandSuccess()
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