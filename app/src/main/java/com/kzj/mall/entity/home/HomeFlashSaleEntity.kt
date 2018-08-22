package com.kzj.mall.entity.home

class HomeFlashSaleEntity : IHomeEntity {
    var dailyBuy: MutableList<DailyBuy>? = null
    var position = 0

    override fun getItemType(): Int {
        return IHomeEntity.FLASH_SALE
    }

    inner class DailyBuy {
        var goodsName: String? = null
        var goodsPrice: String? = null
        var marketPrice: String? = null
        var imgUrl: String? = null
        var goodsInfoId: String? = null
    }
}