package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.RegisterContract
import javax.inject.Inject

@ActivityScope
class RegisterModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), RegisterContract.Model {
    override fun requestRegisterCode(mobile: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestCode(mobile,"1")

    override fun register(mobile: String?, code: String?, password: String?)
            = httpUtils?.obtainRetrofitService(ApiService::class.java)?.register(mobile,code,password)
}