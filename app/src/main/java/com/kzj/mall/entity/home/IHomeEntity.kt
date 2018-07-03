package com.kzj.mall.entity.home

interface IHomeEntity {
    companion object {
        val HEADER_BANNER = 0
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
        val MALE_STATION = 11
        val MALE_SEPCIAL_FIELD = 12
    }

    fun getItemType(): Int
}