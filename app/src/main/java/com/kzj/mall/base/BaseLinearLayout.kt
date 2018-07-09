package com.kzj.mall.base

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yatoooon.screenadaptation.ScreenAdapterTools

abstract class BaseLinearLayout<D : ViewDataBinding> : LinearLayout {
    protected var mBinding: D? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), this, true);
        if (!isInEditMode()) {
            ScreenAdapterTools.getInstance().loadView(mBinding?.root);
        }
        init(attrs!!, defStyleAttr)
    }

    protected abstract fun getLayoutId(): Int;

    protected abstract fun init(attrs: AttributeSet, defStyleAttr: Int)

    fun destory() {
        mBinding?.unbind()
    }
}