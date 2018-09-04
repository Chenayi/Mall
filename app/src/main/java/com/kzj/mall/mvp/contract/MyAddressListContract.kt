package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.AddressEntity
import com.kzj.mall.entity.BaseResponse
import io.reactivex.Observable

interface MyAddressListContract {
    interface View : IView {
        fun showAddress(addressEntity: AddressEntity?)
    }

    interface Model : IModel {
        fun requestAddress(): Observable<BaseResponse<AddressEntity>>?
    }
}