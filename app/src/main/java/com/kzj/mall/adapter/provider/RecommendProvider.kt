package com.kzj.mall.adapter.provider

import android.graphics.Color
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeRecommendEntity
import com.kzj.mall.entity.IHomeEntity

class RecommendProvider : BaseItemProvider<IHomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_recommend
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        val homeRecommendEntity : HomeRecommendEntity = data as HomeRecommendEntity
        homeRecommendEntity?.isShowRecommendText?.let {
            helper?.setGone(R.id.ll_recommend, it)
        }

        val bg = helper?.getView<LinearLayout>(R.id.ll_bg)
        if (homeRecommendEntity.isBackgroundCorners){
            bg?.setBackgroundResource(R.drawable.background_white_corners_8)
        }else{
            bg?.setBackgroundColor(Color.WHITE)
        }
    }
}