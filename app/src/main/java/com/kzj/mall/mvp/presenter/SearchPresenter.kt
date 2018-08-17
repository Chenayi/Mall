package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.SearchContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject
constructor(model: SearchContract.Model?, view: SearchContract.View?, context: Context?)
    : BasePresenter<SearchContract.Model, SearchContract.View>(model, view, context) {

    /**
     * 默认
     */
    fun searchWithDefault(keywords: String) {
        val params = HashMap<String, String>()
        params?.put("keywords", keywords)
        search(params)
    }

    /**
     * 按销量
     */
    fun searchWithSales(keywords: String) {
        val params = HashMap<String, String>()
        params?.put("keywords", keywords)
        params?.put("c_sort", "goods_id")
        search(params)
    }

    /**
     * 按价格
     */
    fun searchWithPrice(keywords: String, order: String) {
        val params = HashMap<String, String>()
        params?.put("keywords", keywords)
        params?.put("c_sort", "shop_price")
        params?.put("c_order", order)
        search(params)
    }

    fun search(params: HashMap<String, String>) {
        model?.search(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SearchEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SearchEntity?) {
                        view?.searchSuccess(t)
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