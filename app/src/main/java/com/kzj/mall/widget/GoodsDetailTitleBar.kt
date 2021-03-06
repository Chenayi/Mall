package com.kzj.mall.widget

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.GoodsDetailNavigatorTitleView
import com.kzj.mall.base.BaseRelativeLayout
import com.kzj.mall.databinding.TitlebarGoodsDetailBinding
import com.kzj.mall.ui.activity.SearchActivity
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

class GoodsDetailTitleBar : BaseRelativeLayout<TitlebarGoodsDetailBinding>, View.OnClickListener {
    private val mTitles: Array<String> = arrayOf("商品", "详情", "资质")
    private var onTabClickListener: OnTabClickListener? = null
    private var onMoreClickListener: OnMoreClickListener? = null
    private var onBackClickListener: OnBackClickListener? = null

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
        mBinding?.ivBack?.setOnClickListener(this)
        mBinding?.ivMore?.setOnClickListener(this)
        mBinding?.ivSearch?.setOnClickListener(this)
    }

    fun setVp(viewPager: ViewPager?) {
        var commonNavigator = CommonNavigator(context)
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = GoodsDetailNavigatorTitleView(context)
                colorTransitionPagerTitleView.normalColor = Color.parseColor("#2E3033")
                colorTransitionPagerTitleView.selectedColor = Color.parseColor("#48B828")
                colorTransitionPagerTitleView.textSize = 15f
                colorTransitionPagerTitleView.setPadding(SizeUtils.dp2px(10f), 0, SizeUtils.dp2px(10f), 0)
                colorTransitionPagerTitleView.setText(mTitles[index])
                colorTransitionPagerTitleView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        viewPager?.currentItem = index
                        onTabClickListener?.onTabClick(index)
                    }
                })
                return colorTransitionPagerTitleView
            }

            override fun getCount(): Int {
                return mTitles?.size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.roundRadius = 9999f
                indicator.setColors(Color.parseColor("#48B828"))
                indicator.lineWidth = SizeUtils.dp2px(13f).toFloat()
                return indicator
            }

        }

        mBinding?.magicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mBinding?.magicIndicator, viewPager);
    }

    fun setTabAlpha(alpha: Float) {
        mBinding?.topView?.alpha = alpha
        mBinding?.rlTab?.alpha = alpha
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackClickListener?.onBackClick()
            }
            R.id.iv_more -> {
                onMoreClickListener?.onMoreClick()
            }
            R.id.iv_search->{
                val intent = Intent(context, SearchActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(intent)
            }
        }
    }

    fun titleSwitch(isShowDetail: Boolean) {
        if (isShowDetail == true) {
            mBinding?.magicIndicator?.visibility = View.INVISIBLE
            mBinding?.tvTitleDetail?.visibility = View.VISIBLE
        } else {
            mBinding?.magicIndicator?.visibility = View.VISIBLE
            mBinding?.tvTitleDetail?.visibility = View.INVISIBLE
        }
    }

    fun setOnTabClickListener(onTabClickListener: OnTabClickListener) {
        this.onTabClickListener = onTabClickListener
    }

    fun setOnMoreClickListener(onMoreClickListener: OnMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener
    }

    fun setOnBackClickListener(onBackClickListener: OnBackClickListener) {
        this.onBackClickListener = onBackClickListener
    }


    interface OnTabClickListener {
        fun onTabClick(p: Int)
    }

    interface OnMoreClickListener {
        fun onMoreClick()
    }

    interface OnBackClickListener {
        fun onBackClick()
    }
}