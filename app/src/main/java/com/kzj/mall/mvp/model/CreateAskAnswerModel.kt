package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class CreateAskAnswerModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), CreateAskAnswerContract.Model {
    override fun interlucationType() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.interlucationType()

    override fun saveInterlucation(catId: String?, content: String?, userAge: String?, userSex: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)
            ?.saveInterlucation(C.TOKEN, catId, content, userAge, userSex)
}