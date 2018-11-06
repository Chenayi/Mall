package com.kzj.mall.entity.order

class LcMap : IGoodsDetail {
    var goodsImg: String? = null
    var goodsInfoName: String? = null
    var goodsInfoNum: String? = null
    var goodsInfoPrice: String? = null
    var goodsMarketingName: String? = null
    var goodsInfoSumPrice:String?=null
    var goodsInfoId: String? = null

    override fun type() = 2
}