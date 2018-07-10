package com.kzj.mall.entity.cart

class CartRecommendEntity : BaseCartEntity() {
    override fun getItemType(): Int {
        return ICart.RECOMMEND
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