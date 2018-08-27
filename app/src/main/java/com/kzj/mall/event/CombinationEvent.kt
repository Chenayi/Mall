package com.kzj.mall.event

import com.kzj.mall.entity.GoodsDetailEntity

class CombinationEvent(isCombination:Boolean,position:Int,combinationList: GoodsDetailEntity.CombinationList) {
    var isCombination = isCombination
    var position = position
    var combinationList: GoodsDetailEntity.CombinationList = combinationList
}