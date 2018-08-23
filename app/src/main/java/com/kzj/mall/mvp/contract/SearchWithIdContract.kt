package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SearchEntity
import io.reactivex.Observable
import java.util.*

interface SearchWithIdContract {
    interface View : IView {
        fun searchSuccess(searchEntity: SearchEntity?)
        fun loadMoreSeccess(searchEntity: SearchEntity?)
    }

    interface Model : IModel {
        fun searchWithCid(params:MutableMap<String,String>?): Observable<BaseResponse<SearchEntity>>?
        fun searchWithBrandId(params:MutableMap<String,String>?): Observable<BaseResponse<SearchEntity>>?
    }
}