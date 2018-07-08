package com.kzj.mall.entity.cart

class CartSingleEntity : BaseCartEntity() {
    override fun getItemType(): Int {
        return ICart.SINGLE
    }
}