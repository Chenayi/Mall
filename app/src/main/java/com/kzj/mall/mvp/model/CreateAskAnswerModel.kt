package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import javax.inject.Inject

@ActivityScope
class CreateAskAnswerModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), CreateAskAnswerContract.Model {
}