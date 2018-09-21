package com.kzj.mall.adapter

import android.content.Context
import android.graphics.Typeface
import com.blankj.utilcode.util.SizeUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class HomeNavigatorTitleView constructor(val mContext:Context?) : ColorTransitionPagerTitleView(mContext) {
    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
        this.setTextSize(15f)
        this.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
        this.setTextSize(15f)
        this.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
    }
}