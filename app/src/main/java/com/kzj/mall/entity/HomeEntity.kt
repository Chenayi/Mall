package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class HomeEntity constructor(val type: Int) : IHomeEntity {
    constructor() : this(0)

    override fun getItemType(): Int {
        return type
    }

    var flashSale: MutableList<FlashSale>? = null
    var advBanner: MutableList<AdvBanner>? = null

    /**
     * 每日闪购
     */
    inner class FlashSale {

    }

    /**
     * 广告banner
     */
    inner class AdvBanner {
        var bannerUrl: String? = null
    }

    /**
     * 情趣用品
     */
    inner class SexToy {

    }
}