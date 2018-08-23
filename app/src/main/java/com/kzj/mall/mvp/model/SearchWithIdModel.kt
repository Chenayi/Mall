package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.SearchWithIdContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class SearchWithIdModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), SearchWithIdContract.Model {
    override fun searchWithCid(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.searchWithCid(params)
    override fun searchWithBrandId(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.searchWithBrandId(params)
}