package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.AliPayKeyEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.ConfirmOrderContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class ConfirmOrderModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), ConfirmOrderContract.Model {
    override fun submitOrder(shoppingCartIds: LongArray?, pay: String?, remark: String?,
                             addressId: String?, shopSumPrice: String?, shopSumshipping: String?) =
            httpUtils?.obtainRetrofitService(ApiService::class.java)?.submitOrder(C.TOKEN,
                    shoppingCartIds,pay,remark,addressId,shopSumPrice,shopSumshipping)

    override fun aliPayKey(orderId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.aliPayKey(C.TOKEN,orderId)
}