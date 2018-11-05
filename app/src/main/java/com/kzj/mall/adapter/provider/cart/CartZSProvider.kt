package com.kzj.mall.adapter.provider.cart

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartZSEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.utils.PriceUtils

/**
 * 赠送
 */
class CartZSProvider : BaseItemProvider<CartZSEntity, BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.item_cart_zs
    }

    override fun viewType(): Int {
        return ICart.ZS
    }

    override fun convert(helper: BaseViewHolder?, data: CartZSEntity?, position: Int) {
        helper?.setText(R.id.tv_goods_name, data?.msMap?.goods_info?.goods_name)
                ?.setText(R.id.tv_goods_num, "x${data?.msMap?.goods_num}")
                ?.setText(R.id.tv_goods_price, PriceUtils.split12sp("¥0.00"))

        GlideApp.with(mContext)
                .load(data?.msMap?.goods_info?.goods_img)
                .centerCrop()
                .placeholder(R.color.gray_f0)
                .into(helper?.getView(R.id.iv_goods) as ImageView)
    }
}