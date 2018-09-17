package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import io.reactivex.Observable

interface MyAskAnswerDetailContract {
    interface View : IView {
        fun myAskAnswer(askAnswerDetailEntity: AskAnswerDetailEntity?)
    }

    interface Model : IModel {
        fun interlucationInfo(qId: String?): Observable<BaseResponse<AskAnswerDetailEntity>>?
    }
}