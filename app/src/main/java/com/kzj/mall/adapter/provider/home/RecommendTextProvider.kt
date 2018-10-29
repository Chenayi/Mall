package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.HomeRecommendTextEntity
import com.kzj.mall.entity.home.IHomeEntity

class RecommendTextProvider : BaseItemProvider<HomeRecommendTextEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_recommend_text_home
    }

    override fun viewType(): Int {
        return IHomeEntity.RECOMMEND_TEXT
    }

    override fun convert(helper: BaseViewHolder?, data: HomeRecommendTextEntity?, position: Int) {
    }
}