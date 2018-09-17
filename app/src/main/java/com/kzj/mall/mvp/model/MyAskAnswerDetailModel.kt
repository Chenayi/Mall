package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class MyAskAnswerDetailModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MyAskAnswerDetailContract.Model {
    override fun interlucationInfo(qId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.interlucationInfo(C.TOKEN, qId)
}