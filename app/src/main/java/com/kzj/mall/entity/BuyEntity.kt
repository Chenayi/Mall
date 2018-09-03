package com.kzj.mall.entity

import java.io.Serializable

class BuyEntity : Serializable {
    var sumPrePrice: String? = null

    var sumOldPrice: String? = null

    var sumPrice: String? = null

    var isFreeShipping: String? = null

    var shippingCharge: Float? = null

    /**
     * 地址列表
     */
    var addressList: MutableList<AddressList>? = null

    /**
     * 当前地址
     */
    var address: Address? = null

    /**
     * 商品列表
     */
    var shoplist: MutableList<Shoplist>? = null

    class AddressList : Serializable {

    }

    class Address : Serializable {
        var addressName: String? = null
        var addressMoblie: String? = null
        //省
        var province: Province? = null
        //市
        var city: City? = null
        //区
        var district: District? = null
        //详细地址
        var addressDetail: String? = null
    }

    class Shoplist : Serializable {
        var appgoods: Appgoods? = null
        var ggList: MutableList<GGList>? = null
    }

    class Appgoods : Serializable {
        var goods_img: String? = null
    }

    class GGList : Serializable {
        var goods_img: String? = null
    }

    /**
     * 省
     */
    class Province : Serializable {
        var provinceId: Long? = null
        var provinceName: String? = null
    }

    /**
     * 市
     */
    class City : Serializable {
        var cityId: Long? = null
        var cityName: String? = null
    }

    /**
     *  区
     */
    class District : Serializable {
        var districtId: Long? = null
        var districtName: String? = null
    }
}