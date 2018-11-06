package com.kzj.mall.entity.home

class HomeChoiceEntity : IHomeEntity {
    var promotionalAd: PromotionalAd? = null
    override fun getItemType(): Int {
        return IHomeEntity.CHOICE
    }

    class PromotionalAd {
        var app_promotional_title: String? = null
        var goods_info_id: Long? = null
    }
}