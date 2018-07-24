package com.kzj.mall.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import android.view.MotionEvent



class ItemScrollView : ScrollView {
    var oldY = 0.0f
    private var t = 0
    private var oldX = 0.0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                val Y = ev.y
                val Ys = Y - oldY
                val X = ev.x

                //滑动到顶部让父控件重新获得触摸事件
                if (Ys > 0 && t === 0) {
                    parent.parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            MotionEvent.ACTION_DOWN -> {
                parent.parent.requestDisallowInterceptTouchEvent(true)
                oldY = ev.y
                oldX = ev.x
            }

            MotionEvent.ACTION_UP -> parent.parent.requestDisallowInterceptTouchEvent(true)

            else -> {
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        this.t = t
        super.onScrollChanged(l, t, oldl, oldt)
    }
}