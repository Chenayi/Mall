package com.kzj.mall.entity

class SearchEntity {
    var results: Results? = null

    class Results {
        var data: MutableList<Data>? = null
    }

    class Data {
        var goods_img: String? = null
        var goods_name: String? = null
        var goods_indication: String? = null
        var goods_id: String? = null
        var goods_info_id: String? = null
        var goods_market_price: String? = null
        var goods_price: String? = null
    }
}