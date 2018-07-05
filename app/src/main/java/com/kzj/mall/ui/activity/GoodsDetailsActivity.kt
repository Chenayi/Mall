package com.kzj.mall.ui.activity

import android.widget.RelativeLayout
import android.widget.ScrollView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.ObservableScrollView


class GoodsDetailsActivity : BaseActivity<IPresenter, ActivityGoodsDetailsBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_goods_details
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mBinding?.goodsDetailTitlebar?.setTabAlpha(0f)
        var screenWidth = ScreenUtils.getScreenWidth()
        var params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(screenWidth, screenWidth)
        mBinding?.rlBanner?.layoutParams = params

        var height = screenWidth
        val titleHeight = SizeUtils.getMeasuredHeight(mBinding?.detailTitleContent)
        val specHeight = SizeUtils.getMeasuredHeight(mBinding?.detailSpec)
        val groudHeight = SizeUtils.getMeasuredHeight(mBinding?.detailGroup)
        val detailHeight = SizeUtils.getMeasuredHeight(mBinding?.detailDetail) + SizeUtils.dp2px(10f)
//                val qualityHeight = SizeUtils.getMeasuredHeight(mBinding?.detailQuality) + SizeUtils.dp2px(10f)

        mBinding?.scrollView?.setOnScrollChangedListener(object : ObservableScrollView.OnScrollChangedListener {
            override fun onScrollChanged(who: ScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                val i = y.toFloat() / height.toFloat()
                if (i <= 1) {
                    mBinding?.goodsDetailTitlebar?.setTabAlpha(i)
                } else {
                    mBinding?.goodsDetailTitlebar?.setTabAlpha(1.0f)
                }


                val detailDistance = height + titleHeight + specHeight + groudHeight - SizeUtils.dp2px(50f)
                val qualityDistance = detailDistance + detailHeight

                if (y < detailDistance) {
                    mBinding?.goodsDetailTitlebar?.switchGoods()
                } else if (y >= detailDistance && y < qualityDistance) {
                    mBinding?.goodsDetailTitlebar?.switchDetail()
                } else {
                    mBinding?.goodsDetailTitlebar?.switchQuality()
                }
            }
        })

        mBinding?.goodsDetailTitlebar?.setOnTabClickListener(object : GoodsDetailTitleBar.OnTabClickListener {
            override fun onTabClick(p: Int) {
                when (p) {

                }
            }
        })

        fun scrollToGoods() {
            mBinding?.scrollView?.scrollTo(0,0)
        }

        fun scrollToDetail() {

        }

        fun scrollToQuality() {

        }
    }
}