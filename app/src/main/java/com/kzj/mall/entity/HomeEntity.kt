package com.kzj.mall.entity

class HomeEntity {
    companion object {
        val CLASSIFY = 1
        val CHOICE = 2
        val BRAND = 3
        val FLASH_SALE = 4
        val CHOICE_GOODS = 5
    }

    var type: Int = 0
    var flashSale: MutableList<FlashSale>? = null

    constructor()

    constructor(type: Int) {
        this.type = type
    }

    inner class FlashSale {

    }

}