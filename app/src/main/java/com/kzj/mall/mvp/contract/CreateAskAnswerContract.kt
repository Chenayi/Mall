package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import io.reactivex.Observable

interface CreateAskAnswerContract {
    interface View : IView {
        fun showInterlucationType(types: MutableList<AskAnswerTypeEntity.CatList>?)

        fun saceInterlucationSuccess()
    }

    interface Model : IModel {
        fun interlucationType(): Observable<BaseResponse<AskAnswerTypeEntity>>?

        fun saveInterlucation(catId: String?, content: String?, userAge: String?, userSex: String?): Observable<BaseResponse<SimpleResultEntity>>?
    }
}