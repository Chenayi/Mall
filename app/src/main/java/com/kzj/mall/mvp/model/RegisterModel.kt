package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.RegisterCodeEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.RegisterContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class RegisterModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), RegisterContract.Model {
    override fun requestRegisterCode(mobile: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestRegisterCode(mobile)
}