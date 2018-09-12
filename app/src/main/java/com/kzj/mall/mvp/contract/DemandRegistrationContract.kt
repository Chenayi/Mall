package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface DemandRegistrationContract {
    interface View : IView {
        fun submitDemandSuccess()
    }

    interface Model : IModel {
        fun demand(params: MutableMap<String, String>?): Observable<BaseResponse<SimpleResultEntity>>?
    }
}