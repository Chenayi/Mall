package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.kzj.mall.adapter.provider.cart.*
import com.kzj.mall.entity.cart.ICart

class CartAdapter constructor(cartDatas:MutableList<ICart>):MultipleItemRvAdapter<ICart,BaseViewHolder>(cartDatas) {

    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(CartZSProvider())
        mProviderDelegate.registerProvider(CartSingleProvider())
        mProviderDelegate.registerProvider(CartGroupProvider())
        mProviderDelegate.registerProvider(CartRecommendTextProvider())
        mProviderDelegate.registerProvider(CartRecommendsProvider())
    }

    override fun getViewType(t: ICart): Int {
        return t?.getItemType()
    }
}