package com.kzj.mall.mvp.model

import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.mvp.contract.MyAddressListContract
import javax.inject.Inject

@ActivityScope
class MyAddressListModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MyAddressListContract.Model {
}