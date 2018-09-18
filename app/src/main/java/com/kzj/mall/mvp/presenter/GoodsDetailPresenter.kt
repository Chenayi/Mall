package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.cart.AddCartEntity
import com.kzj.mall.event.CartChangeEvent
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.GoodsDetailContract
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@ActivityScope
class GoodsDetailPresenter @Inject
constructor(model: GoodsDetailContract.Model?, view: GoodsDetailContract.View?, context: Context?)
    : BasePresenter<GoodsDetailContract.Model, GoodsDetailContract.View>(model, view, context) {

    /**
     * 获取商品详情数据
     */
    fun requesrGoodsDetail(goodsId: String?) {
        val params = HashMap<String, String>()
        val requestGoodsDetailWithLogin: Observable<BaseResponse<GoodsDetailEntity>>?
        if (C.IS_LOGIN) {
            requestGoodsDetailWithLogin = model?.requestGoodsDetailWithLogin(params)
        } else {
            requestGoodsDetailWithLogin = model?.requestGoodsDetail(params)
        }
        goodsId?.let {
            params.put(C.GOODS_INFO_ID, it)
        }

        requestGoodsDetailWithLogin?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<GoodsDetailEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        view?.showLoading()
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: GoodsDetailEntity?) {
                        view?.showGoodsDetail(t)
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
     * 立即购买
     */
    fun buyNow(carType: String, goodsNum: Int?, goodsInfoId: String?, fitId: String?) {
        val params = HashMap<String, String>()
        carType?.let {
            params.put("carType", it)
            //套餐
            if (it.equals("2")) {
                fitId?.let {
                    params.put("fitId", it)
                }
            }
            //单品，疗程
            else {
                goodsInfoId?.let {
                    params.put("goods_info_id", it)
                }
            }
        }

        goodsNum?.let {
            params.put("goodsNum", it.toString())
        }

        model?.buyNow(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<BuyEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: BuyEntity?) {
                        view?.buyNow(t)
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
     * 添加购物车
     */
    fun addCar(carType: String, goodsNum: Int?, goodsInfoId: String?, fitId: String?) {
        val params = HashMap<String, String>()
        carType?.let {
            params.put("carType", it)
            //套餐
            if (it.equals("2")) {
                fitId?.let {
                    params.put("fitId", it)
                }
            }
            //单品，疗程
            else {
                goodsInfoId?.let {
                    params.put("goods_info_id", it)
                }
            }
        }

        goodsNum?.let {
            params.put("goodsNum", it.toString())
        }


        model?.addCar(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AddCartEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: AddCartEntity?) {
                        EventBus.getDefault().post(CartChangeEvent())
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                        view?.addCartError()
                    }

                    override fun onHandleAfter() {
                    }
                })
    }

    /**
     * 处方登记
     */
    fun demandRecord(goodsType: String?, goodsInfoId: String?, fitId: String?) {

        LogUtils.e(goodsType + " , " + goodsInfoId + " , " + fitId)

        val params = HashMap<String, String>()
        goodsType?.let {
            params.put("goodsType", it)
            //套餐
            if (it.equals("2")) {
                fitId?.let {
                    params.put("fitId", it)
                }
            }
            //单品，疗程
            else {
                goodsInfoId?.let {
                    params.put("goods_info_id", it)
                }
            }
        }

        model?.demandRecord(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<BuyEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: BuyEntity?) {
                        view?.demandRecord(t)
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
     * 收藏商品
     */
    fun saveGoodsAtte(goodsInfoId: String?, isAdd: Boolean) {
        model?.saveGoodsAtte(goodsInfoId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        if (isAdd) {
                            view?.colllectSuccess()
                        } else {
                            view?.cancelCollectSuccess()
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