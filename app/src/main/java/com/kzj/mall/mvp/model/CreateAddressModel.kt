package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.CreateAddressContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class CreateAddressModel @Inject
constructor(httpUtils: HttpUtils?)
    : BaseModel(httpUtils), CreateAddressContract.Model {
    override fun addOrUpdateAddress(params: Map<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.addOrUpdateAddress(C.TOKEN, params)
}