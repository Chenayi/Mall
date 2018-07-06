package com.kzj.mall.ui.activity

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.GoodsDetailGroupAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.DataHelper
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.NoScollViewPager
import com.kzj.mall.widget.ObservableScrollView
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView


class GoodsDetailsActivity : BaseActivity<IPresenter, ActivityGoodsDetailsBinding>(), View.OnClickListener {
    private var rvGroup: MultiSnapRecyclerView? = null
    private var goodsDetailGroupAdapter: GoodsDetailGroupAdapter? = null
    private var rlDescribe: RelativeLayout? = null
    private var rlExplain: RelativeLayout? = null
    private var tvDescribe: TextView? = null
    private var tvExplain: TextView? = null
    private var viewDescribe: View? = null
    private var viewExplain: View? = null
    private var vpGoodsDetail: NoScollViewPager? = null

    var detailDistance = 0
    var qualityDistance = 0
    var bannerHeight = 0


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


        mBinding?.scrollView?.setOnScrollChangedListener(object : ObservableScrollView.OnScrollChangedListener {
            override fun onScrollChanged(who: ScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                val i = y.toFloat() / bannerHeight.toFloat()
                if (i <= 1) {
                    mBinding?.goodsDetailTitlebar?.setTabAlpha(i)
                } else {
                    mBinding?.goodsDetailTitlebar?.setTabAlpha(1.0f)
                }


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
                    0 -> scrollToGoods()
                    1 -> scrollToDetail()
                    2 -> scrollToQuality()
                }
            }
        })


        //组合
        initGroupData()
        //详情
        initDetail()
        //资质
        initZizhi()

//        mBinding?.detailGroup?.visibility = View.GONE

        measuredDistance()
    }

    private fun measuredDistance() {
        bannerHeight = SizeUtils.getMeasuredHeight(mBinding?.rlBanner)
        val titleHeight = SizeUtils.getMeasuredHeight(mBinding?.detailTitleContent) - SizeUtils.dp2px(30f)
        val specHeight = SizeUtils.getMeasuredHeight(mBinding?.detailSpec)
        val groudHeight = SizeUtils.getMeasuredHeight(mBinding?.detailGroup)
        val detailHeight = SizeUtils.getMeasuredHeight(mBinding?.detailDetail)

        detailDistance = bannerHeight + titleHeight + specHeight + groudHeight + SizeUtils.dp2px(10f)
        qualityDistance = detailDistance + detailHeight
    }

    /**
     * 组合
     */
    private fun initGroupData() {
        goodsDetailGroupAdapter = GoodsDetailGroupAdapter(DataHelper.goodsDetailGroups())
        rvGroup = findViewById(R.id.rv_group)
        rvGroup?.layoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rvGroup?.adapter = goodsDetailGroupAdapter
    }

    /**
     *详情
     */
    fun initDetail() {
        rlDescribe = findViewById(R.id.rl_describe)
        rlExplain = findViewById(R.id.rl_explain)
        rlDescribe?.setOnClickListener(this)
        rlExplain?.setOnClickListener(this)

        tvDescribe = findViewById(R.id.tv_describe)
        tvExplain = findViewById(R.id.tv_explain)

        viewDescribe = findViewById(R.id.view_describe)
        viewExplain = findViewById(R.id.view_explain)

        vpGoodsDetail = findViewById(R.id.vp_goods_detail)
    }

    /**
     * 资质
     */
    private fun initZizhi() {
        GlideApp.with(this)
                .load(R.mipmap.zhengshu1)
                .centerCrop()
                .into(findViewById(R.id.iv_zizhi1) as ImageView)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu2)
                .centerCrop()
                .into(findViewById(R.id.iv_zizhi2) as ImageView)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu3)
                .centerCrop()
                .into(findViewById(R.id.iv_zizhi3) as ImageView)

        GlideApp.with(this)
                .load(R.mipmap.zhengshu4)
                .centerCrop()
                .into(findViewById(R.id.iv_zizhi4) as ImageView)
    }


    fun scrollToGoods() {
        mBinding?.scrollView?.scrollTo(0, 0)
    }

    fun scrollToDetail() {
        mBinding?.scrollView?.scrollTo(0, detailDistance)
    }

    fun scrollToQuality() {
        mBinding?.scrollView?.scrollTo(0, qualityDistance)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_describe -> {
                tvExplain?.setTextColor(Color.parseColor("#2E3033"))
                tvExplain?.paint?.isFakeBoldText = false
                viewExplain?.visibility = View.GONE

                tvDescribe?.setTextColor(Color.parseColor("#48B828"))
                tvDescribe?.paint?.isFakeBoldText = true
                viewDescribe?.visibility = View.VISIBLE
            }
            R.id.rl_explain -> {
                tvDescribe?.setTextColor(Color.parseColor("#2E3033"))
                tvDescribe?.paint?.isFakeBoldText = false
                viewDescribe?.visibility = View.GONE

                tvExplain?.setTextColor(Color.parseColor("#48B828"))
                tvExplain?.paint?.isFakeBoldText = true
                viewExplain?.visibility = View.VISIBLE
            }
        }
    }
}