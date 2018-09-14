package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.OrderDetailContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class OrderDetailPresenter @Inject
constructor(model: OrderDetailContract.Model, view: OrderDetailContract.View?, context: Context?)
    : BasePresenter<OrderDetailContract.Model, OrderDetailContract.View>(model, view, context) {

    /**
     * 详情数据
     */
    fun orderDetail(orderId:String?){
        model?.orderDetail(orderId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<OrderDetailEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: OrderDetailEntity?) {
                        view?.orderDetail(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun aliPayKey(orderId:String?){
        model?.aliPayKey(orderId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<AliPayKeyEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: AliPayKeyEntity?) {
                        view?.showAliPayKey(t?.payStr)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                    }

                })
    }

    fun takeDelivery(orderId: String?) {
        model?.takeDelivery(orderId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.takeDeliverySuccess()
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