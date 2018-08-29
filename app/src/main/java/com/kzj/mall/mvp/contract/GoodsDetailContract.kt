package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.entity.cart.AddCartEntity
import io.reactivex.Observable

interface GoodsDetailContract {
    interface View : IView {
        fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?)
        fun buyNow(bayEntity: BuyEntity?)
    }

    interface Model : IModel {
        fun requestGoodsDetail(params: MutableMap<String, String>): Observable<BaseResponse<GoodsDetailEntity>>?
        fun buyNow(params: MutableMap<String, String>): Observable<BaseResponse<BuyEntity>>?
        fun addCar(params: MutableMap<String, String>): Observable<BaseResponse<AddCartEntity>>?
    }
}