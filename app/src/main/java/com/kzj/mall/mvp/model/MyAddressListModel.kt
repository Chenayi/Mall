package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.AddressEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.MyAddressListContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class MyAddressListModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MyAddressListContract.Model {
    override fun requestAddress()=httpUtils?.obtainRetrofitService(ApiService::class.java)?.addressList(C.TOKEN)
}