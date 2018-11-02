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
                ?.setText(R.id.tv_goods_num, data?.goods_num?.toString()?.trim())
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_price, goodsPrice)

        val cartType = data?.shopping_cart_type!!
        val promotionMap = data?.promotionMap


        if (!cartType.equals("1") && promotionMap == null) {
            helper?.setGone(R.id.ll_top, false)
        } else {
            helper?.setGone(R.id.ll_top, true)
        }

        //促销
        if (promotionMap != null) {
            val promotionType = promotionMap?.promotion_type!!
            when (promotionType) {
                1 -> helper?.setText(R.id.tv_cuxiao_type, "直降")
                2 -> helper?.setText(R.id.tv_cuxiao_type, "折扣")
                3 -> helper?.setText(R.id.tv_cuxiao_type, "满减")
                4 -> helper?.setText(R.id.tv_cuxiao_type, "满赠")
            }
            helper?.setGone(R.id.ll_cuxiao, true)
                    ?.setText(R.id.tv_cuxiao_name, promotionMap?.promotion_name)
        } else {
            helper?.setGone(R.id.ll_cuxiao, false)
        }

        //已省
        if (!data?.goods_pre_price.equals("0")) {
            val goodsPrePrice = PriceUtils.split12sp("¥" + data?.goods_pre_price)
            helper?.setText(R.id.tv_goods_pre_price, goodsPrePrice)
                    ?.setGone(R.id.ll_goods_pre_price, true)
        } else {
            helper?.setGone(R.id.ll_goods_pre_price, false)
        }

        //疗程
        if (cartType.equals("1")) {
            helper?.setGone(R.id.ll_lc, true)
                    ?.setText(R.id.tv_combination_name, data?.combination_name)
        } else {
            helper?.setGone(R.id.ll_lc, false)
        }

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

        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}