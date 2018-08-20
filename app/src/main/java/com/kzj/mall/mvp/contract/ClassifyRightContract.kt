package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ClassifyRightEntity
import io.reactivex.Observable

interface ClassifyRightContract {
    interface View : IView {
        fun requestClassifyRightSuccess(classifyRightEntity: ClassifyRightEntity?)
    }

    interface Model : IModel {
        fun requestClassifyRight(cid:Int?): Observable<BaseResponse<ClassifyRightEntity>>?
    }
}