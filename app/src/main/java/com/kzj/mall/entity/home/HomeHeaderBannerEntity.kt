package com.kzj.mall.entity.home

class HomeHeaderBannerEntity : IHomeEntity {
    var adss: MutableList<Adds>? = null
    var position = 0
    override fun getItemType(): Int {
        return IHomeEntity.HEADER_BANNER
    }

    inner class Adds {
        var adCode: String? = null
        var goodsInfoId: String? = null
    }
}