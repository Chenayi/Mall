package com.kzj.mall.entity.home

class HomeFlashSaleEntity : IHomeEntity {
    var flashSaleListData: MutableList<FlashSaleListData>? = null
    var position = 0

    override fun getItemType(): Int {
        return IHomeEntity.FLASH_SALE
    }

    inner class FlashSaleListData {

    }
}