package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.ClassifyLeftContract
import javax.inject.Inject

@FragmentScope
class ClassifyLeftModel  @Inject
constructor(httpUtils: HttpUtils?):BaseModel(httpUtils),ClassifyLeftContract.Model{
    override fun requestClassifyLeft() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestClassifyLeft()
}