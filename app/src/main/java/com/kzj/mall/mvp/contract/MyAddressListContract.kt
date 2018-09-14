package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.address.AddressEntity
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.address.Address
import com.kzj.mall.entity.address.CreateAddressEntity
import io.reactivex.Observable

interface MyAddressListContract {
    interface View : IView {
        fun showAddress(addressEntity: AddressEntity?)
        fun addOrUpdateAddressSuccess(t: Address?)
        fun deleteAddressSuccess()
    }

    interface Model : IModel {
        fun requestAddress(): Observable<BaseResponse<AddressEntity>>?
        fun addOrUpdateAddress(params: Map<String, String>?): Observable<BaseResponse<CreateAddressEntity>>?
        fun deleteAddress(addressId: String?): Observable<BaseResponse<SimpleResultEntity>>?
    }
}