package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.LoginEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.LoginContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class LoginModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), LoginContract.Model {
    override fun requestCode(mobile: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestCode(mobile, "2")
    override fun loginByCode(mobile: String?, code: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.loginByCode(mobile, code)
    override fun loginByPassword(username: String?, password: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.loginByPassword(username, password)
}