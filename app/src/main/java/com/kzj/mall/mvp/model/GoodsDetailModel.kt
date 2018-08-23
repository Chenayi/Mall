package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.GoodsDetailEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.GoodsDetailContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class GoodsDetailModel  @Inject
constructor(httpUtils: HttpUtils?): BaseModel(httpUtils), GoodsDetailContract.Model {
    override fun requestGoodsDetail(params: MutableMap<String, String>)=httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestGoodsDetail(params)

}