package com.kzj.mall.event

import com.kzj.mall.entity.GoodsDetailEntity

class GoodSpecChangeEvent(position: Int, goodsDetailEntity: GoodsDetailEntity) {
    var position = position
    var goodsDetailEntity = goodsDetailEntity
}