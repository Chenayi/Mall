package com.kzj.mall.entity

import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.order.OrderDetailEntity
import java.io.Serializable

class CartEntity : Serializable {
    var sumPrePrice: String? = null
    var sumOldPrice: String? = null
    var sumPrice: String? = null

    var shoplist: MutableList<Shoplist>? = null

    var shoppingCart: Shoplist? = null

    class Shoplist : Serializable {
        var goods_num: Int? = null
        var combination_name: String? = null
        var goods_pre_price: String? = null
        var goods_price: String? = null
        var goods_stock: Int? = null
        var goods_info_id: String? = null
        var shopping_cart_type: String? = null
        var appgoods: CartSingleEntity? = null
        var ggList: MutableList<CartGroupEntity.Group>? = null
        var shopping_cart_id: String? = null
        var combinations: MutableList<Combinations>? = null
    }

    class Combinations :Serializable{
        var package_count: Int? = null
        var combination_unit_price: String? = null
    }
}