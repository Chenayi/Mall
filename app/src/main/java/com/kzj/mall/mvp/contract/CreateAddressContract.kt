package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface CreateAddressContract {
    interface View : IView {
        fun addOrUpdateAddressSuccess()
    }

    interface Model : IModel {
        fun addOrUpdateAddress(params: Map<String, String>?): Observable<BaseResponse<SimpleResultEntity>>?
    }
}