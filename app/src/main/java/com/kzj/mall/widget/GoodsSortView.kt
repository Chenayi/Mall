package com.kzj.mall.widget

import android.content.Context
import android.util.AttributeSet
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.GoodsSortViewBinding

class GoodsSortView:BaseRelativeLayout<GoodsSortViewBinding> {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.goods_sort_view
    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
    }
}