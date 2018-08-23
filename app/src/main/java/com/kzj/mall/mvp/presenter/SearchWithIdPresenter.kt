package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.SearchWithIdContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class SearchWithIdPresenter @Inject
constructor(model: SearchWithIdContract.Model?, view: SearchWithIdContract.View?, context: Context?)
    : BasePresenter<SearchWithIdContract.Model, SearchWithIdContract.View>(model, view, context) {

    /**
     * 默认
     */
    fun searchWithCidDefault(cid: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("c_id", cid)
        searchWithCid(params, isLoading, curPage!! > 1)
    }

    fun searchWithBrandIDDefault(brandID: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("brandID", brandID)
        searchWithBrandId(params, isLoading, curPage!! > 1)
    }

    /**
     * 按销量
     */
    fun searchWithCidSales(cid: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("c_id", cid)
        params?.put("c_sort", "goods_id")
        searchWithCid(params, isLoading, curPage!! > 1)
    }

    fun searchWithBrandIDSales(brandID: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("brandID", brandID)
        params?.put("c_sort", "goods_id")
        searchWithBrandId(params, isLoading, curPage!! > 1)
    }

    /**
     * 按价格
     */
    fun searchWithCidPrice(cid: String, order: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("c_id", cid)
        params?.put("c_sort", "shop_price")
        params?.put("c_order", order)
        searchWithCid(params, isLoading, curPage!! > 1)
    }

    fun searchWithBrandIDPrice(brandID: String, order: String, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("brandID", brandID)
        params?.put("c_sort", "shop_price")
        params?.put("c_order", order)
        searchWithBrandId(params, isLoading, curPage!! > 1)
    }

    /**
     * 按类型
     */
    fun searchWithCidType(cid: String, typeWhat: String?, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("c_id", cid)
        typeWhat?.let {
            params?.put("prescription", it)
        }
        searchWithCid(params, isLoading, curPage!! > 1)
    }

    fun searchWithBrandIDType(brandID: String, typeWhat: String?, isLoading: Boolean?, curPage: Int?) {
        val params = HashMap<String, String>()
        params?.put("pageNo", curPage.toString())
        params?.put("pageSize", C.PAGE_SIZE.toString())
        params?.put("brandID", brandID)
        typeWhat?.let {
            params?.put("prescription", it)
        }
        searchWithBrandId(params, isLoading, curPage!! > 1)
    }

    fun searchWithCid(params: HashMap<String, String>, isLoading: Boolean?, isMore: Boolean) {
        model?.searchWithCid(params)
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


    fun searchWithBrandId(params: HashMap<String, String>, isLoading: Boolean?, isMore: Boolean) {
        model?.searchWithBrandId(params)
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