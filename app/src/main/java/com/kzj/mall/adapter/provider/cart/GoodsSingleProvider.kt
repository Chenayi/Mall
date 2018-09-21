package com.kzj.mall.adapter.provider.cart

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartSingleEntity
import com.kzj.mall.entity.cart.GoodsSingleEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.utils.FloatUtils

class GoodsSingleProvider : BaseItemProvider<CartSingleEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_single
    }

    override fun viewType(): Int {
        return ICart.SINGLE
    }

    override fun convert(helper: BaseViewHolder?, data: CartSingleEntity?, position: Int) {

        val allPrice = data?.goods_price?.toFloat()!!
        val num = data?.goods_num!!
        val singlePrice = allPrice / num

        helper?.setGone(R.id.ll_top, data?.shopping_cart_type.equals("1"))
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_price, "¥" + FloatUtils.format(singlePrice))
                ?.setText(R.id.tv_goods_num, "x" + data?.goods_num)
        data?.shopping_cart_type?.let {
            if (it.equals("1")) {
                helper?.setText(R.id.tv_combination_name, data?.combination_name)
                        ?.setText(R.id.tv_all_price, "¥" + FloatUtils.format(data?.goods_price?.toFloat()!!))
                        ?.setTextColor(R.id.tv_goods_price, ContextCompat.getColor(mContext, R.color.gray_6A6E75))

                helper?.getView<TextView>(R.id.tv_goods_price)?.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
            } else {
                helper?.setTextColor(R.id.tv_goods_price, ContextCompat.getColor(mContext, R.color.orange_default))
                helper?.getView<TextView>(R.id.tv_goods_price)?.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
            }
        }
        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }

}