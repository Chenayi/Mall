package com.kzj.mall.entity

import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.order.OrderDetailEntity
import java.io.Serializable
import java.math.BigDecimal

class CartEntity : Serializable {
    var sumPrePrice: String? = null
    var sumOldPrice: String? = null
    var sumPrice: String? = null

    var shoplist: MutableList<Shoplist>? = null

    var shoppingCart: Shoplist? = null

    var orderPromotion: OrderPromotion? = null

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
        var promotionMap: PromotionMap? = null
    }

    class Combinations : Serializable {
        var package_count: Int? = null
        var combination_unit_price: String? = null
    }

    class PromotionMap : Serializable {
        var promotion_type: Int? = null
        var promotion_name: String? = null
    }

    class OrderPromotion {
        var promotion_mjprice: String? = null
        var fullbuyReducePromotions: MutableList<FullbuyReducePromotions>? = null
    }

    class FullbuyReducePromotions {
        var reducePrice: BigDecimal? = null
        var fullPrice: BigDecimal? = null
    }
}