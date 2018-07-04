package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class AndrologySpecialFieldEntity : IHomeEntity {
    var p = 0
    var specialFields: MutableList<SpecialFields>? = null

    override fun getItemType(): Int {
        return IHomeEntity.MALE_SEPCIAL_FIELD
    }

    inner class SpecialFields {

    }
}