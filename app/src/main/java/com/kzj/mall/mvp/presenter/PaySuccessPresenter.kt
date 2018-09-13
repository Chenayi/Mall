package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.PaySuccessContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class PaySuccessPresenter @Inject
constructor(model: PaySuccessContract.Model, view: PaySuccessContract.View?, context: Context?)
    : BasePresenter<PaySuccessContract.Model, PaySuccessContract.View>(model, view, context) {

    fun loadRecommend(pageNo: Int, pageSize: Int) {
        model?.loadRecommend(pageNo, pageSize, "695")
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<HomeRecommendEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: HomeRecommendEntity?) {
                        view?.loadRecommendDatas(t?.results?.data)
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