package com.kzj.mall.mvp.model

import android.database.Observable
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.address.CityEntity
import com.kzj.mall.entity.address.DistrictEntity
import com.kzj.mall.entity.address.ProvinceEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.AddressListContract
import javax.inject.Inject

@FragmentScope
class AddressListModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), AddressListContract.Model {
    override fun requestP() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestP()

    override fun requestC(pid: String?)= httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestC(pid)

    override fun requestD(cid: String?)= httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestD(cid)

}