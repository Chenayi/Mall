package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.VersionEntity
import io.reactivex.Observable

interface MainContract {
    interface View : IView {
        fun versionInfo(versionInfo:VersionEntity?)
    }

    interface Model : IModel {
        fun checkUpdate(systemType: String?): Observable<BaseResponse<VersionEntity>>?
    }
}