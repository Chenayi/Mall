package com.kzj.mall.entity.home

class HomeAdvBannerEntity : IHomeEntity {
    var banners: MutableList<Banners>? = null

    override fun getItemType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    inner class Banners {

    }
}