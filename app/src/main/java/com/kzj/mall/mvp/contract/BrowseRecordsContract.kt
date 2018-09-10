package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BrowseRecordEntity
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface BrowseRecordsContract {
    interface View : IView {
        fun browseRecords(browseRecordEntity: BrowseRecordEntity?)
        fun loadMoreBrowseRecords(browseRecordEntity: BrowseRecordEntity?)
        fun deleteSuccrss()
    }

    interface Model : IModel {
        fun browseRecords(pageNo: Int?, pageSize: Int?): Observable<BaseResponse<BrowseRecordEntity>>?

        fun deleteRecords(likeIds:LongArray?):Observable<BaseResponse<SimpleResultEntity>>?
    }
}