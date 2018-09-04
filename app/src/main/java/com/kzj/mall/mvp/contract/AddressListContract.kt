package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.address.CityEntity
import com.kzj.mall.entity.address.DistrictEntity
import com.kzj.mall.entity.address.ProvinceEntity
import io.reactivex.Observable

interface AddressListContract {
    interface View : IView {
        fun showP(provinceEntity: ProvinceEntity?)
        fun showC(cityEntity: CityEntity?)
        fun showD(districtEntity: DistrictEntity?)
    }

    interface Model : IModel {
        fun requestP(): Observable<BaseResponse<ProvinceEntity>>?
        fun requestC(pid:String?):Observable<BaseResponse<CityEntity>>?
        fun requestD(cid:String?):Observable<BaseResponse<DistrictEntity>>?
    }
}