package com.kzj.mall.entity.cart

class CartGroupEntity : BaseCartEntity() {
    var groups: MutableList<Group>?= null

    override fun getItemType(): Int {
        return ICart.GROUP
    }

    inner class Group {

    }
}