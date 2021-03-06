package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.CodeEntity
import com.kzj.mall.entity.RegisterEntity
import io.reactivex.Observable

interface RegisterContract {
    interface View : IView {
        fun sendCodeSuccess()
        fun sendCodeError(code: Int, errorMsg: String?)
        fun undateCountDownTime(time:Int?)
        fun countDownFinish()
        fun registerSuccess(mobile:String?,registerEntity: RegisterEntity?)
    }

    interface Model : IModel {
        fun requestRegisterCode(mobile: String?): Observable<BaseResponse<CodeEntity>>?
        fun register(mobile: String?,code: String?,password: String?): Observable<BaseResponse<RegisterEntity>>?
    }
}