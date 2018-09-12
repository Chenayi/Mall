package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.MyCollectEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.MyCollectContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class MyCollectModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), MyCollectContract.Model {
    override fun myCollect(goodsType: String?, pageNo: Int, pageSize: Int) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.myFollow(C.TOKEN, goodsType, pageNo, pageSize)
    override fun deleteCollect(followIds: LongArray?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.deleteMyFollow(C.TOKEN,followIds)
}