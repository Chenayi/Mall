package com.kzj.mall.adapter.provider.home

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.utils.PriceUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class RecommendProvider : BaseItemProvider<HomeRecommendEntity.Data, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_recommend_grid
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeRecommendEntity.Data?, position: Int) {
        val ivGoods = helper?.getView<ImageView>(R.id.iv_goods)

        val goodsPrice = PriceUtils.split12sp("¥" + data?.goods_price)
        helper?.setGone(R.id.view_right, data?.isShowRightMargin == true)
                ?.setGone(R.id.view_right2, data?.isShowRightMargin2 == true)
                ?.setGone(R.id.view_left, data?.isShowLeftMargin == true)
                ?.setGone(R.id.view_left2, data?.isShowLeftMargin2 == true)
                ?.setGone(R.id.view_top, data?.isShowTopMargin == true)
                ?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_indication, data?.goods_indication)
                ?.setText(R.id.tv_goods_price, goodsPrice)
                ?.setText(R.id.tv_goods_market_price, "¥" + data?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG

        if (data?.isBackgroundCorners == true) {
            helper?.setBackgroundRes(R.id.ll_bg, R.drawable.background_white_corners_8)
            val multi = MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(SizeUtils.dp2px(8f), 0, RoundedCornersTransformation.CornerType.TOP))
            Glide.with(mContext)
                    .load(data?.goods_img)
                    .apply(RequestOptions.bitmapTransform(multi).placeholder(R.drawable.gray_f5_corners_8))
                    .into(ivGoods!!)
        } else {
            helper?.setBackgroundRes(R.id.ll_bg, R.color.white)
            Glide.with(mContext)
                    .load(data?.goods_img)
                    .apply(RequestOptions().placeholder(R.color.gray_f5))
                    .into(ivGoods!!)
        }
    }
}