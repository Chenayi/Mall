package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.OrderContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class OrderModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), OrderContract.Model {
    override fun myOrderList(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.myOrderList(C.TOKEN, params)

    override fun aliPayKey(orderId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.aliPayKey(C.TOKEN, orderId)

    override fun takeDelivery(orderId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.takeDelivery(C.TOKEN, orderId)
}