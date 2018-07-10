package com.kzj.mall.adapter.provider.cart

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.cart.GoodsSingleEntity
import com.kzj.mall.entity.cart.ICart

class GoodsSingleProvider : BaseItemProvider<GoodsSingleEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: GoodsSingleEntity?, position: Int) {
    }

}