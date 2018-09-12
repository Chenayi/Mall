package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.MyCollectEntity
import com.kzj.mall.entity.SimpleResultEntity
import io.reactivex.Observable

interface MyCollectContract {
    interface View : IView {
        fun myCollect(goodsList: MutableList<MyCollectEntity.FollowList>?)

        fun moreMyCollect(goodsList: MutableList<MyCollectEntity.FollowList>?)

        fun deleteSuccess()
    }

    interface Model : IModel {
        fun myCollect(goodsType: String?, pageNo: Int, pageSize: Int): Observable<BaseResponse<MyCollectEntity>>?

        fun deleteCollect(followIds: LongArray?): Observable<BaseResponse<SimpleResultEntity>>?
    }
}