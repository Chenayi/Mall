package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.SearchContract
import javax.inject.Inject

@ActivityScope
class SearchModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), SearchContract.Model {
    override fun search(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.search(params)
}