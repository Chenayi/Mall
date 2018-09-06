package com.kzj.mall.entity.home

class HomeAdvBannerEntity : IHomeEntity {
    var banners: MutableList<Banners>? = null
    var position = 0

    override fun getItemType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    class Banners {
        var adv: Int? = null
        var goodsInfoId: String? = null
    }
}