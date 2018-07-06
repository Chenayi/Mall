package com.kzj.mall.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup


class NoScollWrapViewPager : ViewPager {
    private var map = HashMap<Int, Int>(2)
    private var currentPage: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0
        if (map.size > currentPage) {
            height = map.get(currentPage)!!
        }
        var h = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, h)
    }

    /**
     * 在切换tab的时候，重置ViewPager的高度
     * @param current
     */
    fun resetHeight(current: Int) {
        this.currentPage = current
        var params: ViewGroup.MarginLayoutParams? = layoutParams as ViewGroup.MarginLayoutParams
        if (map.size > currentPage) {
            if (params == null) {
                params = ViewGroup.MarginLayoutParams(ViewPager.LayoutParams.MATCH_PARENT, map[current]!!)
            } else {
                params!!.height = map[current]!!
            }
            layoutParams = params
        }
    }

    /**
     * 获取、存储每一个tab的高度，在需要的时候显示存储的高度
     * @param current  tab的position
     * @param height   当前tab的高度
     */
    fun addHeight(current: Int, height: Int) {
        map[current] = height
    }
}