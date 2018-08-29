package com.kzj.mall.entity.cart

class CartGroupEntity : BaseCartEntity() {
    var groups: MutableList<Group>? = null
    var goods_pre_price: String? = null
    var combination_name: String? = null
    var goods_price: String? = null

    override fun getItemType(): Int {
        return ICart.GROUP
    }

    class Group {
        var c_goods: CGoods? = null
    }

    class CGoods {
        var goods_img: String? = null
        var goods_name: String? = null
        var goods_price: String? = null
        var goods_info_id: String? = null
    }
}