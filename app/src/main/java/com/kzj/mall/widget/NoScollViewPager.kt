package com.kzj.mall.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NoScollViewPager : ViewPager {
    private var noScroll = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (noScroll)
            false
        else
            super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (noScroll)
            false
        else
            super.onTouchEvent(ev)
    }
}