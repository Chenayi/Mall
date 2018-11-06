package com.kzj.mall.entity.order

class MZMap : IGoodsDetail {
    var goodsMarketingName: String? = null
    var goodsImg: String? = null
    var goodsInfoName: String? = null
    var goodsInfoNum: Int? = null
    var goodsInfoId: String? = null
    override fun type() = 4
}