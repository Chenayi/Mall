package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class SexToyEntity : IHomeEntity {
    var qingqu: MutableList<SexToys>? = null

    override fun getItemType(): Int {
        return IHomeEntity.SEX_TOY
    }

    inner class SexToys {
        var goodsPrice: String? = null
        var goodsName: String? = null
        var imgUrl: String? = null
        var marketPrice: String? = null
        var goodsInfoId: String? = null
    }
}