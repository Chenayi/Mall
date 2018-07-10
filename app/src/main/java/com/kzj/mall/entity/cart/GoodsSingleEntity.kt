package com.kzj.mall.entity.cart

class GoodsSingleEntity : BaseCartEntity() {
    override fun getItemType(): Int {
        return ICart.SINGLE
    }
}