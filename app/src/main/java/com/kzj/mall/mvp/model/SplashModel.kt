package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.base.IModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.RegisterCodeEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.SplashContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class SplashModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), SplashContract.Model {
}