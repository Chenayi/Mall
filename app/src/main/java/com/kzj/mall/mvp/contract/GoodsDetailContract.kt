package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.GoodsDetailEntity
import io.reactivex.Observable

interface GoodsDetailContract {
    interface View : IView {
        fun showGoodsDetail(goodsDetailEntity: GoodsDetailEntity?)
    }

    interface Model : IModel {
        fun requestGoodsDetail(params: MutableMap<String, String>): Observable<BaseResponse<GoodsDetailEntity>>?
    }
}