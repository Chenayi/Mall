package com.kzj.mall.entity

import com.kzj.mall.entity.home.HomeAskAnswerEntity
import com.kzj.mall.entity.home.HomeChoiceEntity
import com.kzj.mall.entity.home.HomeFlashSaleEntity
import com.kzj.mall.entity.home.HomeHeaderBannerEntity

class HomeEntity {
    var adss: MutableList<HomeHeaderBannerEntity.Adds>? = null
    var promotionalAd: HomeChoiceEntity.PromotionalAd? = null
    var dailyBuy: MutableList<HomeFlashSaleEntity.DailyBuy>? = null
    var askList: MutableList<HomeAskAnswerEntity.AskList>? = null
    var qingqu:MutableList<SexToyEntity.SexToys>?=null
}