package com.kzj.mall.entity.home

class HomeHeaderBannerEntity : IHomeEntity {
    var banners: MutableList<Banners>? = null
    var position = 0
    override fun getItemType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    inner class Banners {
        var bannerUrl: String? = null
    }
}