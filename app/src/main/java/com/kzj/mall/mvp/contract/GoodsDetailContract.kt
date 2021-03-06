package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.*
import com.kzj.mall.entity.cart.AddCartEntity
import io.reactivex.Observable

interface GoodsDetailContract {
    interface View : IView {
        fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?)
        fun buyNow(bayEntity: BuyEntity?)
        fun demandRecord(buyEntity: BuyEntity?)
        fun colllectSuccess()
        fun cancelCollectSuccess()
        fun addCartError()
        fun showCartCount(count :Int?)
        fun updateFollowStatus(goodsDetailEntity: GoodsDetailEntity?)
    }

    interface Model : IModel {
        fun requestGoodsDetail(params: MutableMap<String, String>): Observable<BaseResponse<GoodsDetailEntity>>?
        fun requestGoodsDetailWithLogin(params: MutableMap<String, String>): Observable<BaseResponse<GoodsDetailEntity>>?
        fun buyNow(params: MutableMap<String, String>): Observable<BaseResponse<BuyEntity>>?
        fun addCar(params: MutableMap<String, String>): Observable<BaseResponse<CartCountEntitiy>>?
        fun demandRecord(params: MutableMap<String, String>): Observable<BaseResponse<BuyEntity>>?
        fun saveGoodsAtte(goodsInfoId: String?): Observable<BaseResponse<SimpleResultEntity>>?
        fun shoppingCartCount(): Observable<BaseResponse<CartCountEntitiy>>?
    }
}