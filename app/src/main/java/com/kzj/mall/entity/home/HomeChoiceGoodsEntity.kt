package com.kzj.mall.entity.home

class HomeChoiceGoodsEntity : IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.CHOICE_GOODS
    }
}