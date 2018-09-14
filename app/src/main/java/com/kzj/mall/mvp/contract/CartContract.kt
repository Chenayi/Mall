package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.cart.CartRecommendEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.http.api.ApiService
import io.reactivex.Observable

interface CartContract {
    interface View : IView {
        fun showCart(cartEntity: CartEntity?)
        fun deleteCartSuccess(position: Int)
        fun changeCartNumSeccess(position: Int, cartEntity: CartEntity?)
        fun banlance(buyEntity: BuyEntity?)
        fun loadRecommendDatas(t:MutableList<CartRecommendEntity.Data>?)
    }

    interface Model : IModel {
        fun requestCart(): Observable<BaseResponse<CartEntity>>?
        fun deleteCart(cardID: String?): Observable<BaseResponse<SimpleResultEntity>>?
        fun changeCartNum(cardID: String?, num: String): Observable<BaseResponse<CartEntity>>?
        fun cartBanlance(cardIDs: LongArray?): Observable<BaseResponse<BuyEntity>>?
        fun loadRecommendHomeDatas(pageNo: Int, pageSize: Int, cid: String): Observable<BaseResponse<CartRecommendEntity>>?
    }
}