package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.order.ConfirmOrderEntity
import io.reactivex.Observable

interface ConfirmOrderContract {
    interface View : IView {
        fun submitOrderCallBack(confirmOrderEntity: ConfirmOrderEntity?)

        fun showAliPayKey(key: String?)
    }

    interface Model : IModel {
        fun submitOrder(shoppingCartIds: LongArray?, pay: String?, remark: String?, addressId: String?, shopSumPrice: String?, shopSumshipping: String?)
                : Observable<BaseResponse<ConfirmOrderEntity>>?

        fun aliPayKey(orderId: String?): Observable<BaseResponse<AliPayKeyEntity>>?
    }
}