package com.kzj.mall.entity

class HomeRecommendEntity : IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.RECOMMEND
    }

    var isShowRecommendText: Boolean? = null
}