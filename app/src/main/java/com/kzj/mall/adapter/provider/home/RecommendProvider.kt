package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity

class RecommendProvider : BaseItemProvider<HomeRecommendEntity.Data, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item__recommend_goods
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeRecommendEntity.Data?, position: Int) {
        data?.isShowRecommendText?.let {
            helper?.setGone(R.id.ll_recommend, it)
        }

        val bg = helper?.getView<LinearLayout>(R.id.ll_bg)
        if (data?.isBackgroundCorners == true) {
            bg?.setBackgroundResource(R.drawable.background_white_corners_8)
        } else {
            bg?.setBackgroundColor(Color.WHITE)
        }

        helper?.setText(R.id.tv_goods_name, data?.goods_name)
                ?.setText(R.id.tv_goods_indication, data?.goods_indication)
                ?.setText(R.id.tv_price, "¥" + data?.goods_price)

        GlideApp.with(mContext)
                .load(data?.goods_img)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods) as ImageView)
    }
}