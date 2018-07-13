package com.kzj.mall.ui.activity

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.CommomViewPagerAdapter
import com.kzj.mall.adapter.GoodsDetailGroupAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityGoodsDetailsBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.dialog.GoodsSpecDialog
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.ui.fragment.GoodsDetailDescribeFragment
import com.kzj.mall.ui.fragment.GoodsDetailExplainFragment
import com.kzj.mall.widget.GoodsDetailTitleBar
import com.kzj.mall.widget.NoScollWrapViewPager
import com.kzj.mall.widget.ObservableScrollView
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import cn.bingoogolapple.bgabanner.BGABanner


class GoodsDetailsActivity : BaseActivity<IPresenter, ActivityGoodsDetailsBinding>(),
        View.OnClickListener, GoodsDetailDescribeFragment.ChangeHeightListener, GoodsDetailExplainFragment.ChangeHeightListener {
    private var rvGroup: MultiSnapRecyclerView? = null
    private var goodsDetailGroupAdapter: GoodsDetailGroupAdapter? = null
    private var rlDescribe: RelativeLayout? = null
    private var rlExplain: RelativeLayout? = null
    private var tvDescribe: TextView? = null
    private var tvExplain: TextView? = null
    private var viewDescribe: View? = null
    private var viewExplain: View? = null
    private var vpGoodsDetail: NoScollWrapViewPager? = null

    var detailDistance = 0
    var qualityDistance = 0
    var bannerHeight = 0


    override fun getLayoutId(): Int {
        return R.layout.activity_goods_details
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init();
    }

    override fun initData() {
        mBinding?.goodsDetailTitlebar?.setTabAlpha(0f)
        var screenWidth = ScreenUtils.getScreenWidth()
        var params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(screenWidth, screenWidth)
        mBinding?.rlBanner?.layoutParams = params


        mBinding?.scrollView?.setOnScrollChangedListener(object : ObservableScrollView.OnScrollChangedListener {
            override fun onScrollChanged(who: ScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
                val i = y.toFloat() / bannerHeight.toFloat()
                mImmersionBar
                        ?.statusBarDarkFont(i > 0.5f, 0.5f)
                        ?.init();

                mBinding?.goodsDetailTitlebar?.setTabAlpha(if (i < 1f) i else 1f)

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

        //banner
        initBanner()
        //组合
        initGroupData()
        //详情
        initDetail()
        //资质
        initZizhi()
        //点击事件
        initListener()
    }

    private fun initBanner() {
        val advDatas = advDatas()
        mBinding?.banner?.setAdapter(BGABanner.Adapter<ImageView, String> { banner, itemView, model, position ->
            GlideApp.with(this)
                    .load(model)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .dontAnimate()
                    .into(itemView)
        })
        mBinding?.banner?.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mBinding?.tvBannerNum?.setText((position + 1).toString() + "/" + advDatas.size)
            }

        })
        mBinding?.tvBannerNum?.setText("1/" + advDatas.size)
        mBinding?.banner?.setData(advDatas, null)
    }

    private fun advDatas(): MutableList<String> {
        var banners = ArrayList<String>()
        val banner1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861413&di=c9f7439a5a5d4c57435e5eb7f2772817&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a0d4582d8320a84a0e282be8a02e.jpg"
        val banner3 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530523369994&di=60f87ef08f23f8dab2b36d5ed57f5dcd&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ac39597adf9da8012193a352df31.jpg"
        val banner2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530510861412&di=c51db760c9ecc789cdae3b334715aef6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0161c95690b86032f87574beaa54c2.jpg"
        banners.add(banner1)
        banners.add(banner3)
        banners.add(banner2)
        banners.add(banner3)
        return banners
    }

    /**
     * 点击事件
     */
    private fun initListener() {
        mBinding?.detailSpec?.setOnClickListener(this)
        mBinding?.tvAddCart?.setOnClickListener(this)
    }

    /**
     * 计算滑动距离
     */
    private fun measuredDistance() {
        bannerHeight = SizeUtils.getMeasuredHeight(mBinding?.rlBanner)
        val titleHeight = SizeUtils.getMeasuredHeight(mBinding?.detailTitleContent) - BarUtils.getStatusBarHeight() - SizeUtils.dp2px(30f)
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
        goodsDetailGroupAdapter = GoodsDetailGroupAdapter(LocalDatas.goodsDetailGroups())
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


        var fragments = ArrayList<Fragment>()
        fragments.add(GoodsDetailDescribeFragment.newInstance())
        fragments.add(GoodsDetailExplainFragment.newInstance())
        var adapter = CommomViewPagerAdapter(supportFragmentManager, fragments)
        vpGoodsDetail?.adapter = adapter

        vpGoodsDetail?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                vpGoodsDetail?.resetHeight(position)
                measuredDistance()
            }
        })
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
                vpGoodsDetail?.setCurrentItem(0, false)
            }
            R.id.rl_explain -> {
                tvDescribe?.setTextColor(Color.parseColor("#2E3033"))
                tvDescribe?.paint?.isFakeBoldText = false
                viewDescribe?.visibility = View.GONE

                tvExplain?.setTextColor(Color.parseColor("#48B828"))
                tvExplain?.paint?.isFakeBoldText = true
                viewExplain?.visibility = View.VISIBLE
                vpGoodsDetail?.setCurrentItem(1, false)
            }
            R.id.detail_spec -> {
                GoodsSpecDialog.newInstance()
                        .setShowBottom(true)
                        .show(supportFragmentManager)
            }
            R.id.tv_add_cart -> {

            }
        }
    }


    /**
     * 购物车动画
     */
    fun setAddCartAnim(v: View, startLocation: IntArray) {
        //动画层
    }


    override fun changeData(position: Int, height: Int) {
        vpGoodsDetail?.addHeight(position, height)
        vpGoodsDetail?.resetHeight(position)
        measuredDistance()
    }
}