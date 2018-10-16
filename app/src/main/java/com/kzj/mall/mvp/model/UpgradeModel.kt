package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.UpgradeContract
import javax.inject.Inject

@FragmentScope
class UpgradeModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), UpgradeContract.Model {

}