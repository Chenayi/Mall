package com.kzj.mall.adapter.provider.cart

import android.graphics.Paint
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.cart.CartRecommendEntity
import com.kzj.mall.entity.cart.ICart

class CartRecommendsProvider : BaseItemProvider<CartRecommendEntity.Data, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_recommend_grid2
    }

    override fun viewType(): Int {
        return ICart.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: CartRecommendEntity.Data?, position: Int) {

        helper?.setGone(R.id.view_right, data?.isShowRightMargin == true)
                ?.setGone(R.id.view_right2,data?.isShowRightMargin == false)
                ?.setGone(R.id.view_left, data?.isShowLeftMargin == true)

        LogUtils.e("" + data?.isShowRightMargin)
        val ivGoods = helper?.getView<ImageView>(R.id.iv_goods)
        val goodsImageViewWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(61f)) / 2f
        val layoutParams = ivGoods?.layoutParams as LinearLayout.LayoutParams
        layoutParams?.width = goodsImageViewWidth.toInt()
        layoutParams?.height = goodsImageViewWidth.toInt()
        ivGoods?.requestLayout()

        helper?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_indication, data?.goods_indication)
                ?.setText(R.id.tv_goods_price, "¥" + data?.goods_price)
                ?.setText(R.id.tv_goods_market_price, "¥" + data?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线

        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods) as ImageView)
    }

}