package com.kzj.mall.entity.order

class TcMap:IGoodsDetail {
    var tcMaps: MutableList<OrderDetailEntity.TCMap>? = null
    override fun type() = 3
}