package com.kzj.mall.adapter.provider.cart

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.ICart

class CartSingleProvider : BaseItemProvider<CartSingleEntity, BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.item_cart_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {
        val ivCheckDelete = helper?.getView<ImageView>(R.id.iv_check)
        //删除模式
        if (data?.isDeleteMode == true) {
            ivCheckDelete?.setImageResource(R.color.gray_default)
        }
        //正常选择模式
        else {
            ivCheckDelete?.setImageResource(if (data?.isCheck == true) R.mipmap.icon_cart_check else R.color.gray_default)
        }
        helper?.addOnClickListener(R.id.iv_check)
    }
}