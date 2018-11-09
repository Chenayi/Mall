package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.MineEntity
import com.kzj.mall.entity.VersionEntity
import io.reactivex.Observable

interface MineContract {
    interface View : IView {
        fun showMineData(mineEntity: MineEntity?)
        fun versionInfo(versionInfo:VersionEntity?)
    }

    interface Model : IModel {
        fun requestMine(): Observable<BaseResponse<MineEntity>>?
        fun checkUpdate(systemType: String?): Observable<BaseResponse<VersionEntity>>?
    }
}