package com.kzj.mall.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.SearchBarBinding

class SearchBar : BaseRelativeLayout<SearchBarBinding>, View.OnClickListener {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId() = R.layout.search_bar

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        mBinding?.ivBack?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                (context as Activity).finish()
            }
        }
    }


}