package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity

class RecommendProvider : BaseItemProvider<HomeRecommendEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item__recommend_goods
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeRecommendEntity?, position: Int) {
        val homeRecommendEntity  = data
        homeRecommendEntity?.isShowRecommendText?.let {
            helper?.setGone(R.id.ll_recommend, it)
        }

        val bg = helper?.getView<LinearLayout>(R.id.ll_bg)
        if (homeRecommendEntity?.isBackgroundCorners == true){
            bg?.setBackgroundResource(R.drawable.background_white_corners_8)
        }else{
            bg?.setBackgroundColor(Color.WHITE)
        }
    }
}