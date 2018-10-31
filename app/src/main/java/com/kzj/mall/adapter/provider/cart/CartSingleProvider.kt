package com.kzj.mall.adapter.provider.cart

import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.utils.PriceUtils

class CartSingleProvider : BaseItemProvider<CartSingleEntity, BaseViewHolder>() {

    override fun layout(): Int {
        return R.layout.item_cart_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {
        val ivCheckDelete = helper?.getView<ImageView>(R.id.iv_check)
        ivCheckDelete?.setImageResource(if (data?.isCheck == true) R.mipmap.icon_cart_check else R.mipmap.check_nor)
        val goodsPrice = PriceUtils.split12sp("¥" + data?.goods_price)
        helper?.addOnClickListener(R.id.fl_check)
                ?.addOnClickListener(R.id.iv_minus)
                ?.addOnClickListener(R.id.iv_plus)
                ?.setGone(R.id.ll_lc, data?.shopping_cart_type.equals("1"))
                ?.setGone(R.id.ll_goods_pre_price, data?.shopping_cart_type.equals("1"))
                ?.setText(R.id.tv_goods_num, data?.goods_num?.toString()?.trim())
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_price, goodsPrice)

        if (data?.goods_stock!! <= 0) {
            helper?.getView<LinearLayout>(R.id.ll_container)?.alpha = 0.5f
            helper?.setGone(R.id.tv_no_stock, true)
        } else {
            helper?.getView<LinearLayout>(R.id.ll_container)?.alpha = 1f
            helper?.setGone(R.id.tv_no_stock, false)
        }

        val ivMinus = helper?.getView<ImageView>(R.id.iv_minus)
        val ivPlus = helper?.getView<ImageView>(R.id.iv_plus)
        data?.goods_num?.let {
            if (it <= 1) {
                ivMinus?.isEnabled = false
                ivMinus?.setImageResource(R.mipmap.minus_nor)
            } else {
                ivMinus?.isEnabled = true
                ivMinus?.setImageResource(R.mipmap.minus_cart)
            }

            val goodsStock = data?.goods_stock
            ivPlus?.isEnabled = it < goodsStock!!
        }

        data?.shopping_cart_type?.let {
            //疗程
            if (it.equals("1")) {
                val goodsPrePrice = PriceUtils.split12sp("¥" + data?.goods_pre_price)
                helper?.setText(R.id.tv_goods_pre_price, goodsPrePrice)
                        ?.setText(R.id.tv_combination_name, data?.combination_name)
            }
        }


        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}