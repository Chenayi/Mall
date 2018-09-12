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
    var caddress: Address? = null

    /**
     * 处方商品
     */
    var goodsinfoMap: GoodsinfoMap? = null

    /**
     * 商品列表
     */
    var shoplist: MutableList<CartEntity.Shoplist>? = null


    /**
     * 处方商品
     */
    class GoodsinfoMap : Serializable {
        var goods_img: String? = null
        var goods_name: String? = null
        var goods_price: String? = null
        var goods_type: String? = null
        var goods_info_added: String? = null
        var goods_spec: String? = null
    }
}