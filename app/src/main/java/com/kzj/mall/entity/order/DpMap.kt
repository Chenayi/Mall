package com.kzj.mall.entity.order

class DpMap : IGoodsDetail {
    var goodsImg: String? = null
    var goodsInfoName: String? = null
    var goodsInfoNum: String? = null
    var goodsInfoPrice: String? = null
    var goodsInfoSumPrice: String? = null
    var goodsInfoId: String? = null

    override fun type() = 0
}