package com.kzj.mall.entity.order

class LcMap : IGoodsDetail {
    var goodsImg: String? = null
    var goodsInfoName: String? = null
    var goodsInfoNum: String? = null
    var goodsInfoPrice: String? = null
    var goodsMarketingName: String? = null

    override fun type() = 2
}