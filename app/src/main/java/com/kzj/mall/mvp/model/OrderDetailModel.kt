package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.OrderDetailContract
import javax.inject.Inject

@ActivityScope
class OrderDetailModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), OrderDetailContract.Model {
    override fun orderDetail(orderId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.orderDetail(C.TOKEN, orderId)

    override fun aliPayKey(orderId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.aliPayKey(C.TOKEN,orderId)
}