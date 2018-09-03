package com.kzj.mall.event

import com.kzj.mall.entity.GoodsDetailEntity

class GoodSpecChangeEvent(position: Int, spec: String, goodsInfoId: String, goodsDetailEntity: GoodsDetailEntity) {
    var position = position
    var spec = spec
    var goodsInfoId = goodsInfoId
    var goodsDetailEntity = goodsDetailEntity
}