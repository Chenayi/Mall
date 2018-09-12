package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.DemandRegistrationContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class DemandRegistrationModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), DemandRegistrationContract.Model {
    override fun demand(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.submitDemandReord(C.TOKEN,params)
}