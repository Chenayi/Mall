package com.kzj.mall.entity

import com.kzj.mall.entity.home.IHomeEntity

class SexToyEntity : IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.SEX_TOY
    }
}