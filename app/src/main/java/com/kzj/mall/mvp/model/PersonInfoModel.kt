package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.CustomerEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.PersonInfoContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class PersonInfoModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), PersonInfoContract.Model {
    override fun customerInfo() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.customerInfo(C.TOKEN)
    override fun updateInfo(params: MutableMap<String, String>?) = httpUtils?.obtainRetrofitService(ApiService::class.java)
            ?.updateInfo(C.TOKEN, params)
}