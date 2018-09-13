package com.kzj.mall.entity.home

class HomeRecommendEntity {
    var results: Results? = null

    class Results {
        var data: MutableList<Data>? = null
    }

    class Data : IHomeEntity {
        /**
         * 是否显示为您推荐
         */
        var isShowRecommendText: Boolean = false

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