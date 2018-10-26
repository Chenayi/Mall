package com.kzj.mall.adapter

import android.graphics.Paint
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity

class PaySuccessAdapter(recommendDatas: MutableList<HomeRecommendEntity.Data>)
    : BaseAdapter<HomeRecommendEntity.Data, BaseViewHolder>(R.layout.item_recommend_grid2, recommendDatas) {
    override fun convert(helper: BaseViewHolder?, item: HomeRecommendEntity.Data?) {
        val layoutPosition = helper?.layoutPosition!!

        if (layoutPosition % 2 == 0) {
            helper?.setGone(R.id.view_right,true)
                    ?.setGone(R.id.view_right2,false)
                    ?.setGone(R.id.view_left, false)
        }else{
            helper?.setGone(R.id.view_right,false)
                    ?.setGone(R.id.view_right2,true)
                    ?.setGone(R.id.view_left, true)
        }

        if (layoutPosition == 1 || layoutPosition == 2){
            helper?.setGone(R.id.view_top,false)
        }else{
            helper?.setGone(R.id.view_top,true)
        }

        val ivGoods = helper?.getView<ImageView>(R.id.iv_goods)
        val goodsImageViewWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(61f)) / 2f
        val layoutParams = ivGoods?.layoutParams as LinearLayout.LayoutParams
        layoutParams?.width = goodsImageViewWidth.toInt()
        layoutParams?.height = goodsImageViewWidth.toInt()
        ivGoods?.requestLayout()

        helper?.setText(R.id.tv_goods_name, item?.goods_name)
                ?.setText(R.id.tv_goods_indication, item?.goods_indication)
                ?.setText(R.id.tv_goods_price, "¥" + item?.goods_price)
                ?.setText(R.id.tv_goods_market_price, "¥" + item?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.getPaint()?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线

        GlideApp.with(mContext)
                .load(item?.goods_img)
                .centerCrop()
                .placeholder(R.color.gray_default)
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}