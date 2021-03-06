package com.kzj.mall.entity.cart

import com.kzj.mall.entity.CartEntity
import java.io.Serializable

class CartGroupEntity : BaseCartEntity(), Serializable {
    var groups: MutableList<Group>? = null
    var goods_pre_price: String? = null
    var combination_name: String? = null
    var goods_price: String? = null
    var promotionMap: CartEntity.PromotionMap? = null

    override fun getItemType(): Int {
        return ICart.GROUP
    }

    class Group : Serializable {
        var c_goods: CGoods? = null
        var goodsNum: String? = null

    }

    class CGoods : Serializable {
        var goods_img: String? = null
        var goods_name: String? = null
        var goods_price: String? = null
        var goods_info_id: String? = null
        var goods_num: String? = null
        var goods_stock: Int? = null
    }
}