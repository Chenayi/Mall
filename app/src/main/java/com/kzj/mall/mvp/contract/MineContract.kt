package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.MineEntity
import io.reactivex.Observable

interface MineContract {
    interface View : IView {
        fun showMineData(mineEntity: MineEntity?)
    }

    interface Model : IModel {
        fun requestMine(): Observable<BaseResponse<MineEntity>>?
    }
}