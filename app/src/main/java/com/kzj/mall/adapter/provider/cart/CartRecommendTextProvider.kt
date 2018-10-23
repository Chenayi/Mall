package com.kzj.mall.adapter.provider.cart

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartRecommendTextEntity
import com.kzj.mall.entity.cart.ICart

class CartRecommendTextProvider:BaseItemProvider<CartRecommendTextEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_recommend_text
    }

    override fun viewType(): Int {
        return ICart.RECOMMEND_TEXT
    }

    override fun convert(helper: BaseViewHolder?, data: CartRecommendTextEntity?, position: Int) {
    }
}