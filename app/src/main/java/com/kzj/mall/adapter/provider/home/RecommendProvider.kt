package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.makeramen.roundedimageview.RoundedImageView

class RecommendProvider : BaseItemProvider<HomeRecommendEntity.Data, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_recommend_grid
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeRecommendEntity.Data?, position: Int) {
        val ivGoods = helper?.getView<RoundedImageView>(R.id.iv_goods)
        val goodsImageViewWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(29f)) / 2f
        val layoutParams = ivGoods?.layoutParams as LinearLayout.LayoutParams
        layoutParams?.width = goodsImageViewWidth.toInt()
        layoutParams?.height = goodsImageViewWidth.toInt()
        ivGoods?.requestLayout()

        val bg = helper?.getView<LinearLayout>(R.id.ll_bg)
        if (data?.isBackgroundCorners == true) {
            bg?.setBackgroundResource(R.drawable.background_white_corners_8)
            ivGoods?.setCornerRadius(SizeUtils.dp2px(8f).toFloat(),SizeUtils.dp2px(8f).toFloat(),0f,0f)
        } else {
            bg?.setBackgroundColor(Color.WHITE)
            ivGoods?.setCornerRadius(0f,0f,0f,0f)
        }

        helper?.setGone(R.id.view_right, data?.isShowRightMargin == true)
                ?.setGone(R.id.view_right2, data?.isShowRightMargin == false)
                ?.setGone(R.id.view_left, data?.isShowLeftMargin == true)
                ?.setGone(R.id.view_top, data?.isShowTopMargin == true)
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_indication, data?.goods_indication)
                ?.setText(R.id.tv_goods_price, "¥" + data?.goods_price)
                ?.setText(R.id.tv_goods_market_price, "¥" + data?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG

        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(ivGoods!!)
    }
}