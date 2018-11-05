package com.kzj.mall.entity.cart

import com.kzj.mall.entity.CartEntity
import java.io.Serializable

class CartZSEntity : BaseCartEntity(), Serializable {
    var msMap: CartEntity.MSMap?=null

    override fun getItemType(): Int {
        return ICart.ZS
    }
}