package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.order.OrderEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.OrderContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class OrderPresenter @Inject
constructor(model: OrderContract.Model?, view: OrderContract.View?, context: Context?)
    : BasePresenter<OrderContract.Model, OrderContract.View>(model, view, context) {


    fun myOrderList(orderStatus: Int?, pageNo: Int, isMore: Boolean, isShowLoading: Boolean) {
        val params = HashMap<String, String>()
        orderStatus?.let {
            params.put("order_status", it?.toString())
        }

        params?.put("pageNo", pageNo.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())


        model?.myOrderList(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<OrderEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (isShowLoading) {
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: OrderEntity?) {
                        if (!isMore) {
                            view?.showOrders(t?.orderList?.list)
                        } else {
                            view?.loadMoreOrders(t?.orderList?.list)
                        }
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun aliPayKey(orderId: String?) {
        model?.aliPayKey(orderId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AliPayKeyEntity>() {
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