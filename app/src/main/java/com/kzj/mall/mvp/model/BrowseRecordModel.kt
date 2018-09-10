package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BrowseRecordEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.BrowseRecordsContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class BrowseRecordModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), BrowseRecordsContract.Model {
    override fun browseRecords(pageNo: Int?, pageSize: Int?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.browseRecords(C.TOKEN, pageNo, pageSize)

    override fun deleteRecords(likeIds: LongArray?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.deleteBrowseRecords(C.TOKEN, likeIds)
}