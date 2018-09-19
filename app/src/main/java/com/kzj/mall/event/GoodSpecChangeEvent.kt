package com.kzj.mall.event

import com.kzj.mall.entity.GoodsDetailEntity

class GoodSpecChangeEvent(goodsDetailEntity: GoodsDetailEntity) {
    var goodsDetailEntity = goodsDetailEntity
}