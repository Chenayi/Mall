package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.PaySuccessContract
import javax.inject.Inject

@ActivityScope
class PaySuccessModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), PaySuccessContract.Model {
    override fun loadRecommend(pageNo: Int, pageSize: Int, cid: String) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.loadRecommendHomeDatas(pageNo, pageSize, cid)
}