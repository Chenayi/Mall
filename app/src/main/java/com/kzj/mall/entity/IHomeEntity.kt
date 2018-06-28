package com.kzj.mall.entity

interface IHomeEntity {
    companion object {
        val CLASSIFY = 1
        val CHOICE = 2
        val BRAND = 3
        val FLASH_SALE = 4
        val CHOICE_GOODS = 5
        val ADV_BANNER = 6
        val SICKNESS = 7
        val SEX_TOY = 8
        val ASK_ANSWER = 9
        val RECOMMEND = 10
    }

    fun getItemType(): Int
}