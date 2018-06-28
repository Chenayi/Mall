package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeRecommendEntity
import com.kzj.mall.entity.IHomeEntity

class RecommendProvider : BaseItemProvider<IHomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_recommend
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        val homeRecommendEntity : HomeRecommendEntity = data as HomeRecommendEntity
        homeRecommendEntity?.isShowRecommendText?.let {
            helper?.setGone(R.id.ll_recommend, it)
        }
    }
}