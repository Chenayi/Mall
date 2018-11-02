package com.kzj.mall.entity.cart

import com.kzj.mall.entity.CartEntity
import java.io.Serializable

class CartSingleEntity : BaseCartEntity(), Serializable {
    var goods_img: String? = null
    var goods_pre_price: String? = null
    var combination_name: String? = null
    var goods_name: String? = null
    var goods_price: String? = null
    var goods_info_id: String? = null
    var shopping_cart_type: String? = null
    var promotionMap: CartEntity.PromotionMap? = null

    override fun getItemType(): Int {
        return ICart.SINGLE
    }
}