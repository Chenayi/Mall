package com.kzj.mall.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class LoopViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var onPageChangeListener = object :OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }
        override fun onPageSelected(position: Int) {
        }
    }
}