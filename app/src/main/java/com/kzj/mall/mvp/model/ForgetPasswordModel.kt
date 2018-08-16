package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.ForgetPasswordContract
import javax.inject.Inject

@ActivityScope
class ForgetPasswordModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), ForgetPasswordContract.Model {
    override fun requestCode(mobile: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestCode(mobile, "2")

    override fun resetPassword(mobile: String?, code: String?, newPassword: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.resetPassword(mobile, code, newPassword)
}