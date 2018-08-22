package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.HomeRecommendEntity
import io.reactivex.Observable

interface HomeContract {
    interface View : IView {
        fun showHomeDatas(homeEntity: HomeEntity?)
        fun loadRecommendDatas(homeRecommendEntity: HomeRecommendEntity?)
    }

    interface Model : IModel {
        fun requestAndrologyDatas(): Observable<BaseResponse<HomeEntity>>?
        fun requestHomeDatas(): Observable<BaseResponse<HomeEntity>>?
        fun loadRecommendHomeDatas(pageNo: Int, pageSize: Int, cid: String): Observable<BaseResponse<HomeRecommendEntity>>?
    }
}