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

    var msMap: MSMap? = null

    class MSMap :Serializable{
        var goods_info: GoodsInfo? = null
        var goods_num: Int? = null
    }

    class GoodsInfo :Serializable{
        var goods_img: String? = null
        var goods_name: String? = null
        var goods_info_id: String? = null
        var goods_stock: Int = 0
    }

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
        var mzList: MutableList<MZList>? = null
    }

    class MZList :Serializable{
        var goods_img: String? = null
        var goodsNum: Int? = null
        var goods_name: String? = null
        var openImage = false
    }

    class OrderPromotion :Serializable{
        var promotion_mjprice: String? = null
        var fullbuyReducePromotions: MutableList<FullbuyReducePromotions>? = null
    }

    class FullbuyReducePromotions :Serializable{
        var reducePrice: BigDecimal? = null
        var fullPrice: BigDecimal? = null
    }
}