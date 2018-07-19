package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.RegisterCodeEntity
import io.reactivex.Observable

interface RegisterContract {
    interface View : IView {
        fun sendCodeSuccess()
        fun sendCodeError(code: Int, errorMsg: String?)
    }

    interface Model : IModel {
        fun requestRegisterCode(mobile: String?): Observable<BaseResponse<RegisterCodeEntity>>?
    }
}