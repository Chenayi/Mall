package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.MyAskAnswerContract
import javax.inject.Inject

@FragmentScope
class MyAskAnswerModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MyAskAnswerContract.Model {
    override fun myAskAnswer(qStatus: String?, pageNo: Int?, pageSize: Int?) = httpUtils?.obtainRetrofitService(ApiService::class.java)
            ?.askanswer(C.TOKEN, qStatus, pageNo, pageSize)
}