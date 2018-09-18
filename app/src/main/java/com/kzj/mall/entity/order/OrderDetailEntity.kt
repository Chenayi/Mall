package com.kzj.mall.entity.order

class OrderDetailEntity {

    companion object {
        val TYPE_SINGLE = 0
        val TYPE_GROUP = 1
        val TYPE_COURSE = 2
    }


    var order: Order? = null

    var ordergoods: OrderGoods? = null

    class Order {
        var orderStatus: Int? = null
        var shippingPerson: String? = null
        var shippingMobile: String? = null
        var shippingProvince: String? = null
        var shippingCity: String? = null
        var shippingCounty: String? = null
        var shippingAddress: String? = null
        var orderNo: String? = null
        var addTime: Long? = null
        var moneyPaid: String? = null
        var oldPrice: String? = null
        var prePrice: String? = null
        var shippingFee: String? = null
        var expressno: MutableList<Expressno>? = null
        var orderLinePay: String? = null
    }

    class OrderGoods {
        //单品
        var dpMap: MutableList<DPMap>? = null
        //疗程
        var lcMap: MutableList<LCMap>? = null
        //套餐
        var tcMap: MutableList<TCMap>? = null
    }

    /**
     * 单品
     */
    class DPMap : IGoodsDetail {
        var goods: Goods? = null
    }

    /**
     * 疗程
     */
    class LCMap : IGoodsDetail {
        var goods: Goods? = null
        var combination: Combination? = null
    }

    /**
     * 组合套餐
     */
    class TCMap : IGoodsDetail {
        var goods: MutableList<Goods>? = null
        var combination: Combination? = null
    }

    /**
     * 商品
     */
    class Goods {
        var goodsImg: String? = null
        var goodsName: String? = null
        var goodsInfoSumPrice: String? = null
        var goodsNum: String? = null
        var goodsPrice: String? = null
    }

    /**
     * 套餐信息
     */
    class Combination {
        var combination_name: String? = null
    }

    /**
     * 物流
     */
    class Expressno {
        var expressNo: String? = null
        var express: String? = null
        var relationId: String? = null
    }
}