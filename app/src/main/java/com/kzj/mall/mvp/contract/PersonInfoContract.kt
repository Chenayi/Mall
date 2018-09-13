package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.CustomerEntity
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface PersonInfoContract {
    interface View : IView {
        fun showPersonInfo(info: CustomerEntity.CustAllInfo?)
    }

    interface Model : IModel {
        fun customerInfo(): Observable<BaseResponse<CustomerEntity>>?

        fun updateInfo(params: MutableMap<String, String>?): Observable<SimpleResultEntity>?
    }
}