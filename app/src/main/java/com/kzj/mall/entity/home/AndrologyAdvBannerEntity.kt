package com.kzj.mall.entity.home

class AndrologyAdvBannerEntity : IHomeEntity {
    var banners: MutableList<Banners>? = null

    override fun getItemType(): Int {
        return IHomeEntity.MALE_ADV_BANNER
    }

    inner class Banners{

    }
}