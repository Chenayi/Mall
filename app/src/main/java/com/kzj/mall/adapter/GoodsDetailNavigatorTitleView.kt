package com.kzj.mall.adapter

import android.content.Context
import com.blankj.utilcode.util.SizeUtils
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class GoodsDetailNavigatorTitleView constructor(val mContext:Context?) : ColorTransitionPagerTitleView(mContext) {
    override fun onSelected(index: Int, totalCount: Int) {
        super.onSelected(index, totalCount)
        val paint = this.paint
        paint.isFakeBoldText = true
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        super.onDeselected(index, totalCount)
        val paint = this.paint
        paint.isFakeBoldText = false
    }
}