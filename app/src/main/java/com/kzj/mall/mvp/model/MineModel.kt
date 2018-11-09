package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.MineEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.MineContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class MineModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MineContract.Model {
    override fun requestMine() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestMine(C.TOKEN)
    override fun checkUpdate(systemType: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.checkUpdate(systemType)
}