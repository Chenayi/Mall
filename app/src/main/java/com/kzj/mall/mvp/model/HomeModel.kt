package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.HomeContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class HomeModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), HomeContract.Model {
    override fun requestHomeDatas() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requeseHomeDatas()
}