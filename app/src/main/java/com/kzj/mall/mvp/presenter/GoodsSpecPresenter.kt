package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.GoodsSpecContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class GoodsSpecPresenter @Inject
constructor(model: GoodsSpecContract.Model?, view: GoodsSpecContract.View?, context: Context?)
    : BasePresenter<GoodsSpecContract.Model, GoodsSpecContract.View>(model, view, context) {

    /**
     * 获取商品详情数据
     */
    fun requesrGoodsDetail(position: Int, spec: String?, goodsInfoId: String?) {
        val params = HashMap<String, String>()

        if (C.IS_LOGIN) {
            params.put("token", C.TOKEN)
        }
        goodsInfoId?.let {
            params.put(C.GOODS_INFO_ID, it)
        }

        model?.requestGoodsDetail(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<GoodsDetailEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        view?.showLoading()
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: GoodsDetailEntity?) {
                        view?.showGoodsDetail(position, spec, goodsInfoId, t)
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