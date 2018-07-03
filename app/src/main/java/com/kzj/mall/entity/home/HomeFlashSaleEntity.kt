package com.kzj.mall.entity.home

class HomeFlashSaleEntity : IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.FLASH_SALE
    }
}