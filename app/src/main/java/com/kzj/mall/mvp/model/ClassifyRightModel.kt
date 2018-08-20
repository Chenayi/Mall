package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ClassifyRightEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.ClassifyRightContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class ClassifyRightModel  @Inject
constructor(httpUtils: HttpUtils?): BaseModel(httpUtils),ClassifyRightContract.Model{
    override fun requestClassifyRight(cid :Int?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestClassifyRight(cid)
}