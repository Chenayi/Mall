package com.kzj.mall.entity.home

class HomeRecommendEntity {
    var results: Results? = null

    class Results {
        var data: MutableList<Data>? = null
    }

    class Data : IHomeEntity {
        /**
         * 是否显示右边距
         */
        var isShowRightMargin: Boolean = false
        var isShowRightMargin2: Boolean = false

        /**
         * 是否显示左边距
         */
        var isShowLeftMargin: Boolean = false
        var isShowLeftMargin2: Boolean = false

        /**
         * 是否显示上边距
         */
        var isShowTopMargin:Boolean = false
        /**
         * 是否圆角
         */
        var isBackgroundCorners: Boolean = false

        var goods_img: String? = null
        var goods_name: String? = null
        var goods_indication: String? = null
        var goods_id: String? = null
        var goods_info_id: String? = null
        var goods_market_price: String? = null
        var goods_price: String? = null

        override fun getItemType() = IHomeEntity.RECOMMEND
    }
}