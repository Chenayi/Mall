package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.HomeEntity
import io.reactivex.Observable

interface HomeContract {
    interface View : IView {
        fun showHomeDatas(homeEntity: HomeEntity?)
    }

    interface Model : IModel {
        fun requestHomeDatas(): Observable<BaseResponse<HomeEntity>>?
    }
}