package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.HomeContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class HomePresenter @Inject
constructor(model: HomeContract.Model?, view: HomeContract.View?, context: Context?)
    : BasePresenter<HomeContract.Model, HomeContract.View>(model, view, context) {

    /**
     * 首页
     */
    fun requestHomeDatas() {
        model?.requestHomeDatas()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<HomeEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: HomeEntity?) {
                        view?.showHomeDatas(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }


    fun loadRecommendDatas(pageNo: Int, pageSize: Int) {
        model?.loadRecommendHomeDatas(pageNo, pageSize, "695")
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<HomeRecommendEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: HomeRecommendEntity?) {
                        view?.loadRecommendDatas(t)
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