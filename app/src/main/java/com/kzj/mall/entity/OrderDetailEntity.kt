package com.kzj.mall.entity

class OrderDetailEntity {

    companion object {
        val TYPE_SINGLE = 0
        val TYPE_GROUP = 1
        val TYPE_COURSE = 2
    }

    var type: Int? = null
    var orderGoods: MutableList<OrderGoods>? = null

    class OrderGoods {

    }
}