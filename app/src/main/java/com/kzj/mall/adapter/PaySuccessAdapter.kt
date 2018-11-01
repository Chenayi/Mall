package com.kzj.mall.adapter

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.utils.PriceUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class PaySuccessAdapter(recommendDatas: MutableList<HomeRecommendEntity.Data>)
    : BaseAdapter<HomeRecommendEntity.Data, BaseViewHolder>(R.layout.item_recommend_grid, recommendDatas) {
    override fun convert(helper: BaseViewHolder?, item: HomeRecommendEntity.Data?) {
        val layoutPosition = helper?.layoutPosition!!

        if (layoutPosition % 2 == 0) {
            helper?.setGone(R.id.view_right, true)
                    ?.setGone(R.id.view_right2, false)
                    ?.setGone(R.id.view_left, false)
                    ?.setGone(R.id.view_left2, true)
        }else{
            helper?.setGone(R.id.view_right, false)
                    ?.setGone(R.id.view_right2, true)
                    ?.setGone(R.id.view_left, true)
                    ?.setGone(R.id.view_left2, false)
        }

        if (layoutPosition == 1 || layoutPosition == 2){
            helper?.setGone(R.id.view_top,false)
        }else{
            helper?.setGone(R.id.view_top,true)
        }

        val ivGoods = helper?.getView<ImageView>(R.id.iv_goods)

        val goodsPrice = PriceUtils.split12sp("¥" + item?.goods_price)
        helper?.setText(R.id.tv_goods_name, item?.goods_name)
                ?.setText(R.id.tv_goods_indication, item?.goods_indication)
                ?.setText(R.id.tv_goods_price, goodsPrice)
                ?.setText(R.id.tv_goods_market_price, "¥" + item?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线

        val multi = MultiTransformation(
                CenterCrop(),
                RoundedCornersTransformation(SizeUtils.dp2px(8f), 0, RoundedCornersTransformation.CornerType.TOP))
        Glide.with(mContext)
                .load(item?.goods_img)
                .apply(RequestOptions.bitmapTransform(multi).placeholder(R.drawable.gray_f5_corners_8))
                .into(ivGoods!!)
    }
}