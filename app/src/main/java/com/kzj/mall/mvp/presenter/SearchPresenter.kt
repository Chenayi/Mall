package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.C
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
    fun searchWithDefault(keywords: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("keywords", keywords)
        search(params, isLoading, curPage!! > 1)
    }

    /**
     * 按销量
     */
    fun searchWithSales(keywords: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("keywords", keywords)
        params?.put("c_sort", "goods_id")
        search(params, isLoading, curPage!! > 1)
    }

    /**
     * 按价格
     */
    fun searchWithPrice(keywords: String, order: String, isLoading: Boolean?, curPage: Int?) {

        LogUtils.e("curPage ===> " + curPage)

        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("keywords", keywords)
        params?.put("c_sort", "shop_price")
        params?.put("c_order", order)
        search(params, isLoading, curPage!! > 1)
    }

    /**
     * 按类型
     */
    fun searchWithType(keywords: String, typeWhat: String?, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("keywords", keywords)
        typeWhat?.let {
            params?.put("prescription", it)
        }
        search(params, isLoading, curPage!! > 1)
    }

    fun search(params: HashMap<String, String>, isLoading: Boolean?, isMore: Boolean) {
        model?.search(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SearchEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (isLoading == true) {
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: SearchEntity?) {
                        if (!isMore){
                            view?.searchSuccess(t)
                        }else{
                            view?.loadMoreSeccess(t)
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
}