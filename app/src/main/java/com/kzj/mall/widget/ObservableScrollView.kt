package com.kzj.mall.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.widget.ScrollView

class ObservableScrollView : ScrollView {
    private var mOnScrollChangedListener: OnScrollChangedListener? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener?.onScrollChanged(this, l, t, oldl, oldt)
        }
    }

    fun setOnScrollChangedListener(listener: OnScrollChangedListener) {
        mOnScrollChangedListener = listener
    }

    interface OnScrollChangedListener {
        fun onScrollChanged(who: ObservableScrollView, l: Int, t: Int, oldl: Int, oldt: Int)
    }
}