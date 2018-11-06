package com.kzj.mall.entity.order

class OrderDetailEntity {
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
        var dpMap: MutableList<DpMap>? = null
        //疗程
        var lcMap: MutableList<LcMap>? = null
        //套餐
        var tcMap: MutableList<MutableList<TCMap>>? = null
        //买赠
        var mzMap: MutableList<MZMap>? = null
        //赠品
        var zpMap: MutableList<ZPMap>? = null
    }

    class TCMap {
        var goodsImg: String? = null
        var goodsInfoName: String? = null
        var goodsInfoNum: String? = null
        var goodsInfoPrice: String? = null
        var goodsMarketingName: String? = null
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