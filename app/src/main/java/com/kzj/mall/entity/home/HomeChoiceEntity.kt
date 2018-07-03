package com.kzj.mall.entity.home

class HomeChoiceEntity :  IHomeEntity {
    override fun getItemType(): Int {
        return IHomeEntity.CHOICE
    }
}