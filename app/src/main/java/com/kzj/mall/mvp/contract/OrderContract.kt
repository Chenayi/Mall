package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.order.OrderEntity
import io.reactivex.Observable

interface OrderContract {
    interface View : IView {
        fun showOrders(orders:MutableList<OrderEntity.List>?)
        fun loadMoreOrders(orders: MutableList<OrderEntity.List>?)
        fun showAliPayKey(key: String?)
    }

    interface Model : IModel {
        fun myOrderList(params :MutableMap<String,String>?): Observable<BaseResponse<OrderEntity>>?

        fun aliPayKey(orderId: String?): Observable<BaseResponse<AliPayKeyEntity>>?
    }
}