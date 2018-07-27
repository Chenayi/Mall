package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.LoginContract
import javax.inject.Inject

@FragmentScope
class LoginModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), LoginContract.Model {

}