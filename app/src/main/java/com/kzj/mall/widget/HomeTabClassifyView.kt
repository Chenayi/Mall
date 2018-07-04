package com.kzj.mall.widget

import android.content.Context
import android.util.AttributeSet
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.HomeTabClassifyBinding

class HomeTabClassifyView : BaseRelativeLayout<HomeTabClassifyBinding> {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId(): Int {
       return R.layout.home_tab_classify
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
    }
}