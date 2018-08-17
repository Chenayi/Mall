package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SearchEntity
import io.reactivex.Observable
import java.util.*

interface SearchContract {
    interface View : IView {
        fun searchSuccess(searchEntity: SearchEntity?)
    }

    interface Model : IModel {
        fun search(params:MutableMap<String,String>?): Observable<BaseResponse<SearchEntity>>?
    }
}