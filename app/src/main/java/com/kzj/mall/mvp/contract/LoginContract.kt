package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.CodeEntity
import com.kzj.mall.entity.LoginEntity
import io.reactivex.Observable

interface LoginContract {
    interface View : IView {
        fun sendCodeSuccess()
        fun sendCodeError(code: Int, errorMsg: String?)
        fun undateCountDownTime(time:Int?)
        fun countDownFinish()
        fun loginSuccess(loginEntity: LoginEntity?)
    }

    interface Model : IModel {
        fun requestCode(mobile: String?):Observable<BaseResponse<CodeEntity>>?
        fun loginByCode(mobile: String?,code: String?): Observable<BaseResponse<LoginEntity>>?
        fun loginByPassword(username: String?,password:String?): Observable<BaseResponse<LoginEntity>>?
    }
}