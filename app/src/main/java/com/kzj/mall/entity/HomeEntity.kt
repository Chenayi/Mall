package com.kzj.mall.entity

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

    }

    /**
     * 情趣用品
     */
    inner class SexToy {

    }
}