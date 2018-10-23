package com.kzj.mall.entity.cart

import java.io.Serializable

class CartRecommendTextEntity:ICart,Serializable {
    override fun getItemType(): Int {
        return ICart.RECOMMEND_TEXT
    }
}