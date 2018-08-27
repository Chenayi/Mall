package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.GoodsDetailContract
import io.reactivex.disposables.Disposable
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

        if (C.IS_LOGIN) {

        }
        goodsId?.let {
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

    fun buyNow(carType: String, goodsNum: Int?, goodsInfoId: String?, fitId: String?) {
        val params = HashMap<String, String>()
        carType?.let {
            params.put("carType",it)
            //套餐
            if (it.equals("2")){
                fitId?.let {
                    params.put("fitId",it)
                }
            }
            //单品，疗程
            else{
                goodsInfoId?.let {
                    params.put("goods_info_id",it)
                }
            }
        }

        goodsNum?.let {
            params.put("goodsNum",it.toString())
        }

        model?.buyNow(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<BuyEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: BuyEntity?) {

                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }
                })
    }
}