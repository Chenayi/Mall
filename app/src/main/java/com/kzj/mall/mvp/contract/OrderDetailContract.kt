package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.order.OrderDetailEntity
import io.reactivex.Observable

interface OrderDetailContract {
    interface View : IView {
        fun orderDetail(orderDetailEntity: OrderDetailEntity?)

        fun showAliPayKey(key: String?)
    }

    interface Model : IModel {
        fun orderDetail(orderId: String?): Observable<BaseResponse<OrderDetailEntity>>?

        fun aliPayKey(orderId: String?): Observable<BaseResponse<AliPayKeyEntity>>?
    }
}