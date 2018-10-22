package com.kzj.mall.entity.cart

class CartRecommendEntity {
    var results: Results? = null

    class Results {
        var data: MutableList<Data>? = null
    }

    class Data : BaseCartEntity() {
        /**
         * 是否显示为您推荐
         */
        var isShowRecommendText: Boolean = false

        /**
         * 是否显示右边距
         */
        var isShowRightMargin: Boolean = false

        /**
         * 是否显示左边距
         */
        var isShowLeftMargin: Boolean = false

        var goods_img: String? = null
        var goods_name: String? = null
        var goods_indication: String? = null
        var goods_id: String? = null
        var goods_info_id: String? = null
        var goods_market_price: String? = null
        var goods_price: String? = null

        override fun getItemType() = ICart.RECOMMEND
    }
}