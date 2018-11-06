package com.kzj.mall.entity.order

class ZPMap : IGoodsDetail {
    var goodsImg: String? = null
    var goodsInfoName: String? = null
    var goodsInfoNum: String? = null

    override fun type(): Int {
        return 5
    }
}