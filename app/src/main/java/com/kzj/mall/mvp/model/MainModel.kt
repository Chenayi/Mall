package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.MainContract
import javax.inject.Inject

@ActivityScope
class MainModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MainContract.Model {

}