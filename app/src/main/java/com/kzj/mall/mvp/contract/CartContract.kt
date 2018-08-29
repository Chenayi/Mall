package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface CartContract {
    interface View : IView {
        fun showCart(cartEntity: CartEntity?)
        fun deleteCartSuccess(position: Int)
        fun changeCartNumSeccess(position: Int, cartEntity: CartEntity?)
        fun banlance(buyEntity: BuyEntity?)
    }

    interface Model : IModel {
        fun requestCart(): Observable<BaseResponse<CartEntity>>?
        fun deleteCart(cardID: String?): Observable<BaseResponse<SimpleResultEntity>>?
        fun changeCartNum(cardID: String?, num: String): Observable<BaseResponse<CartEntity>>?
        fun cartBanlance(cardIDs: LongArray?): Observable<BaseResponse<BuyEntity>>?
    }
}