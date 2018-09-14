package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.ask.AskAnswerEntity
import io.reactivex.Observable

interface MyAskAnswerContract {
    interface View : IView {
        fun showAskAnswer(datas: MutableList<AskAnswerEntity.List>?)
        fun loadMoreAskAnswer(datas: MutableList<AskAnswerEntity.List>?)
    }

    interface Model : IModel {
        fun myAskAnswer(qStatus: String?, pageNo: Int?, pageSize: Int?): Observable<BaseResponse<AskAnswerEntity>>?
    }
}