package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.home.HomeRecommendEntity
import io.reactivex.Observable

interface PaySuccessContract {
    interface View : IView {
        fun loadRecommendDatas(recommends: MutableList<HomeRecommendEntity.Data>?)
    }

    interface Model : IModel {
        fun loadRecommend(pageNo: Int, pageSize: Int, cid: String): Observable<BaseResponse<HomeRecommendEntity>>?
    }
}