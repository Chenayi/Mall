package com.kzj.mall.event

class PackageListEvent(isCombination: Boolean, position: Int, goodsInfoId: String, goodsPrice: String, goodsMarketPrice: String,combinaName:String) {
    var isCombination = isCombination
    var position = position
    var goodsInfoId = goodsInfoId
    var goodsPrice = goodsPrice
    var goodsMarketPrice = goodsMarketPrice
    var combinaName = combinaName
}