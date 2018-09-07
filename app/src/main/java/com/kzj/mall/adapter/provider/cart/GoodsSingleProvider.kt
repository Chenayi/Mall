package com.kzj.mall.adapter.provider.cart

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.GoodsSingleEntity
import com.kzj.mall.entity.cart.ICart

class GoodsSingleProvider : BaseItemProvider<CartSingleEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {
        helper?.setGone(R.id.ll_top, data?.shopping_cart_type.equals("1"))
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_price, "¥" + data?.goods_price)
                ?.setText(R.id.tv_goods_num, "x" + data?.goods_num)
        data?.shopping_cart_type?.let {
            if (it.equals("1")) {
                helper?.setText(R.id.tv_combination_name, data?.combination_name)
            }
        }
        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }

}