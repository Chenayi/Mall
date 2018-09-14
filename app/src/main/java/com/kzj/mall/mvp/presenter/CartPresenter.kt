package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.cart.CartRecommendEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.CartContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class CartPresenter @Inject
constructor(model: CartContract.Model?, view: CartContract.View?, context: Context?)
    : BasePresenter<CartContract.Model, CartContract.View>(model, view, context) {

    /**
     * 获取购物车数据
     */
    fun requesrCart(isLoading: Boolean) {
        model?.requestCart()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CartEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        if (isLoading) {
                            view?.showLoading()
                        }
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: CartEntity?) {
                        view?.showCart(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }


    /**
     * 删除购物车
     */
    fun deleteCart(position: Int, cartID: String?) {
        model?.deleteCart(cartID)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.deleteCartSuccess(position)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    /**
     * 修改数量
     */
    fun changeCartNum(position: Int, cartID: String?, num: String) {
        model?.changeCartNum(cartID, num)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CartEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: CartEntity?) {
                        view?.changeCartNumSeccess(position, t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }

    /**
     * 购物车结算
     */
    fun cartBanlance(cartIDs:LongArray?){
        model?.cartBanlance(cartIDs)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<BuyEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: BuyEntity?) {
                        view?.banlance(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun loadRecommendsData(){
        model?.loadRecommendHomeDatas(1, C.PAGE_SIZE,"695")
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CartRecommendEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: CartRecommendEntity?) {
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