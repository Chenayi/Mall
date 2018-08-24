package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class AndrologySpecialFieldEntity : IHomeEntity {

    companion object {
        val TYPE_ZIBU = 0
        val TYPE_QINRE = 1
    }

    var p = 0
    var specialFields: MutableList<SpecialFields>? = null
    var type = TYPE_ZIBU

    override fun getItemType(): Int {
        return IHomeEntity.MALE_SEPCIAL_FIELD
    }

    class SpecialFields {
        var goodsInfoId: String? = null
        var imgUrl: String? = null
        var goodsName: String? = null
        var goodsPrice: String? = null
    }
}