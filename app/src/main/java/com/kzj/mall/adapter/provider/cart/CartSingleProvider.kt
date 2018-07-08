package com.kzj.mall.adapter.provider.cart

import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.ICart

class CartSingleProvider : BaseItemProvider<CartSingleEntity,BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.item_cart_single
    }

    override fun viewType(): Int {
       return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {
        helper?.setVisible(R.id.iv_check,data?.isCheck!!)
    }
}