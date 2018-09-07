package com.kzj.mall.entity

import com.kzj.mall.entity.address.Address
import java.io.Serializable

class BuyEntity : Serializable {
    var sumPrePrice: String? = null

    var sumOldPrice: String? = null

    var sumPrice: String? = null

    var isFreeShipping: String? = null

    var shippingCharge: Float? = null

    var shoppingCartIds: LongArray? = null

    /**
     * 地址列表
     */
    var addressList: MutableList<Address>? = null

    /**
     * 当前地址
     */
    var address: Address? = null

    /**
     * 商品列表
     */
    var shoplist: MutableList<CartEntity.Shoplist>? = null

//    class Shoplist : Serializable {
//        var appgoods: Appgoods? = null
//        var ggList: MutableList<GGList>? = null
//        var shopping_cart_type:String?=null
//    }
//
//    class Appgoods : Serializable {
//        var goods_img: String? = null
//    }
//
//    class GGList : Serializable {
//        var goods_img: String? = null
//    }


}