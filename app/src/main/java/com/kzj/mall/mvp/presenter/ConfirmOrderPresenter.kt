package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.order.ConfirmOrderEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.ConfirmOrderContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class ConfirmOrderPresenter @Inject
constructor(model: ConfirmOrderContract.Model, view: ConfirmOrderContract.View?, context: Context?)
    : BasePresenter<ConfirmOrderContract.Model, ConfirmOrderContract.View>(model, view, context) {

    fun submitOrder(shoppingCartIds: LongArray?, pay: String?, remark: String?, addressId: String?, shopSumPrice: String?, shopSumshipping: String?) {

//            LogUtils.e(shoppingCartIds?.get(0)?.toString()+"\n"+pay+"\n"+remark+"\n"+addressId+"\n"+shopSumPrice+"\n"+shopSumshipping)

              model?.submitOrder(shoppingCartIds,pay,remark,addressId,shopSumPrice,shopSumshipping)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<ConfirmOrderEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: ConfirmOrderEntity?) {
                        view?.submitOrderCallBack(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
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

}