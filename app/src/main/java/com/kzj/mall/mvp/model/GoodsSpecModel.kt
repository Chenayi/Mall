package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.GoodsSpecContract
import javax.inject.Inject

@FragmentScope
class GoodsSpecModel  @Inject
constructor(httpUtils: HttpUtils?): BaseModel(httpUtils), GoodsSpecContract.Model {
    override fun requestGoodsDetail(params: MutableMap<String, String>)=httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestGoodsDetail(params)

}