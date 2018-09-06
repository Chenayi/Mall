package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ConfirmOrderEntity
import io.reactivex.Observable

interface ConfirmOrderContract {
    interface View : IView {
        fun submitOrderCallBack(confirmOrderEntity: ConfirmOrderEntity?)
    }

    interface Model : IModel {
        fun submitOrder(shoppingCartIds: LongArray?, pay: String?, remark: String?, addressId: String?, shopSumPrice: String?, shopSumshipping: String?)
                : Observable<BaseResponse<ConfirmOrderEntity>>?
    }
}