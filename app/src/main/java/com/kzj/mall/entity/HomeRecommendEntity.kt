package com.kzj.mall.entity

class HomeRecommendEntity : IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.RECOMMEND
    }

    /**
     * 是否显示为您推荐
     */
    var isShowRecommendText: Boolean = false

    /**
     * 是否圆角
     */
    var isBackgroundCorners: Boolean = false
}