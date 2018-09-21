package com.kzj.mall.entity.cart

abstract class BaseCartEntity : ICart {
    var isCheck = false
    var shopping_cart_id: String? = null
    var goods_num: Int? = null
    var goods_stock: Int? = null
    var goodsNum: Int? = null
}