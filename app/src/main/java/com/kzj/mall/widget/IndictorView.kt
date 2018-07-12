package com.kzj.mall.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseLinearLayout
import com.kzj.mall.databinding.IndicatorViewBinding
import android.widget.LinearLayout


class IndictorView : BaseLinearLayout<IndicatorViewBinding> {
    private var count = 0//指示器的数量
    private var selectIndicatorResid = R.drawable.indicator_sel//选中时候的图片
    private var noSelectIndicatorResid = R.drawable.indicator_default //未选中的时候的图片
    private var indicatorWidhtDp = 0//指示器的宽
    private var indictorHeightDp = 0//指示器的高
    private var indicatorMarginLeft = 0 //指示器MarginLeft
    private var beforeSelectIndex = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId(): Int {
        return R.layout.indicator_view
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
        selectIndicatorResid = array.getResourceId(R.styleable.IndicatorView_indicator_s_resid, R.drawable.indicator_sel);
        noSelectIndicatorResid = array.getResourceId(R.styleable.IndicatorView_indicator_n_resid, R.drawable.indicator_default);
        indicatorWidhtDp = array.getDimensionPixelSize(R.styleable.IndicatorView_indicator_width, SizeUtils.dp2px(12f));
        indictorHeightDp = array.getDimensionPixelSize(R.styleable.IndicatorView_indicator_width, SizeUtils.dp2px(4f));
        indicatorMarginLeft = array.getDimensionPixelOffset(R.styleable.IndicatorView_indicator_margin_left, SizeUtils.dp2px(6f));
        array.recycle()

        if (count != 0) {
            setIndicatorsSize(count);
        }
    }


    /**
     * 设置指示器
     */
    fun setIndicatorsSize(count: Int) {
        for (i in 0 until count) {
            val imageView = ImageView(context)
            imageView.setImageResource(selectIndicatorResid)
            mBinding?.widgetIndicatorLayout?.addView(imageView)
            val params = imageView.layoutParams as LinearLayout.LayoutParams
            params.height = indictorHeightDp
            params.width = indicatorWidhtDp
            //第一个不需要margin
            if (i != 0) {
                params.leftMargin = indicatorMarginLeft
                imageView.setImageResource(noSelectIndicatorResid)
            }
            imageView.setLayoutParams(params)
        }

    }


    /**
     * 设置选中的indicator
     *
     * @param index
     */
    fun setSelectIndex(index: Int) {
        val imageViewbefore = mBinding?.widgetIndicatorLayout?.getChildAt(beforeSelectIndex) as ImageView
        val imageViewCureent = mBinding?.widgetIndicatorLayout?.getChildAt(index) as ImageView
        imageViewbefore?.setImageResource(noSelectIndicatorResid)
        imageViewCureent?.setImageResource(selectIndicatorResid)
        beforeSelectIndex = index
    }
}