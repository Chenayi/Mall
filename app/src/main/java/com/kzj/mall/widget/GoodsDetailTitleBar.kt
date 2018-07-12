package com.kzj.mall.widget

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.TitlebarGoodsDetailBinding

class GoodsDetailTitleBar : BaseRelativeLayout<TitlebarGoodsDetailBinding>, View.OnClickListener {
    private var onTabClickListener: OnTabClickListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getLayoutId(): Int {
        return R.layout.titlebar_goods_detail
    }

    override fun init(attrs: AttributeSet, defStyleAttr: Int) {
        var layoutParams = mBinding?.topView?.layoutParams
        layoutParams?.width = ScreenUtils.getScreenWidth()
        layoutParams?.height = BarUtils.getStatusBarHeight()
        mBinding?.topView?.requestLayout()

        mBinding?.tvGoods?.paint?.isFakeBoldText = true
        mBinding?.tvGoods?.setOnClickListener(this)
        mBinding?.tvDetail?.setOnClickListener(this)
        mBinding?.tvQuality?.setOnClickListener(this)
        mBinding?.tvQuality?.setOnClickListener(this)
        mBinding?.ivBack?.setOnClickListener(this)
    }

    fun setTabAlpha(alpha: Float) {
        mBinding?.topView?.alpha = alpha
        mBinding?.rlTab?.alpha = alpha
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_goods -> {
                switchGoods()
                onTabClickListener?.onTabClick(0)
            }
            R.id.tv_detail -> {
                switchDetail()
                onTabClickListener?.onTabClick(1)
            }
            R.id.tv_quality -> {
                switchQuality()
                onTabClickListener?.onTabClick(2)
            }
            R.id.iv_back->{
                (context as Activity).finish()
            }
        }
    }

    /**
     * 商品
     */
    fun switchGoods() {
        setDefault()
        mBinding?.tvGoods?.paint?.isFakeBoldText = true
        mBinding?.tvGoods?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    /**
     *  详情
     */
    fun switchDetail() {
        setDefault()
        mBinding?.tvDetail?.paint?.isFakeBoldText = true
        mBinding?.tvDetail?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    /**
     * 资质
     */
    fun switchQuality() {
        setDefault()
        mBinding?.tvQuality?.paint?.isFakeBoldText = true
        mBinding?.tvQuality?.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun setOnTabClickListener(onTabClickListener: OnTabClickListener) {
        this.onTabClickListener = onTabClickListener
    }

    private fun setDefault() {
        mBinding?.tvGoods?.paint?.isFakeBoldText = false
        mBinding?.tvGoods?.setTextColor(Color.parseColor("#2E3033"))

        mBinding?.tvDetail?.paint?.isFakeBoldText = false
        mBinding?.tvDetail?.setTextColor(Color.parseColor("#2E3033"))

        mBinding?.tvQuality?.paint?.isFakeBoldText = false
        mBinding?.tvQuality?.setTextColor(Color.parseColor("#2E3033"))
    }

    interface OnTabClickListener {
        fun onTabClick(p: Int)
    }
}