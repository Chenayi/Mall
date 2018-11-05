package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.kzj.mall.adapter.provider.cart.GoodsGroupProvider
import com.kzj.mall.adapter.provider.cart.GoodsSingleProvider
import com.kzj.mall.adapter.provider.cart.GoodsZSProvider
import com.kzj.mall.entity.cart.ICart

class OrderGoodsListAdapter constructor(cartDatas: MutableList<ICart>) : MultipleItemRvAdapter<ICart, BaseViewHolder>(cartDatas) {
    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(GoodsZSProvider())
        mProviderDelegate.registerProvider(GoodsSingleProvider())
        mProviderDelegate.registerProvider(GoodsGroupProvider())
    }

    override fun getViewType(t: ICart): Int {
        return t?.getItemType()
    }
}