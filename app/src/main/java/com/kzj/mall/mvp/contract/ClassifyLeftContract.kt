package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ClassifyLeftEntity
import io.reactivex.Observable

interface ClassifyLeftContract {
    interface View : IView {
        fun requestClassifyLeftSuccess(classifyLeftEntity: ClassifyLeftEntity?)
    }

    interface Model : IModel {
        fun requestClassifyLeft(): Observable<BaseResponse<ClassifyLeftEntity>>?
    }
}