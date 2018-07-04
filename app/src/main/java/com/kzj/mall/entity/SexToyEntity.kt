package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class SexToyEntity : IHomeEntity {
    var sexToys: MutableList<SexToys>? = null

    override fun getItemType(): Int {
        return IHomeEntity.SEX_TOY
    }

    inner class SexToys {

    }
}