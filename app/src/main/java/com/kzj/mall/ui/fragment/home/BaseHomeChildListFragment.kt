package com.kzj.mall.ui.fragment.home

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.HeaderBannerProvider
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.databinding.FragmentBaseHomeChildListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerHomeComponent
import com.kzj.mall.di.module.HomeModule
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.mvp.contract.HomeContract
import com.kzj.mall.mvp.presenter.HomePresenter
import com.kzj.mall.widget.ExpandLoadMoewView
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.ViewConfiguration
import android.view.animation.*
import com.kzj.mall.event.BackClassifyEvent
import com.kzj.mall.ui.activity.*
import org.greenrobot.eventbus.EventBus


abstract class BaseHomeChildListFragment : BaseFragment<HomePresenter, FragmentBaseHomeChildListBinding>(), HomeContract.View {
    private var listAdapter: ListAdapter? = null
    protected var headerBannerProvider: HeaderBannerProvider? = null
    protected var arrowVisiable = false
    protected var bannerColorRes: Int? = null
    private var isAskVisible = true
    private var distance = 0
    private var firstVisibleItemPosition = 0

    private var isBarPrimaryColor = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bannerColorRes = ContextCompat.getColor(context!!, R.color.colorPrimary)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_home_child_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(HomeModule(this))
                .build()
                .inject(this)
    }

    fun bannerColorRes() = bannerColorRes

    fun isBarPrimaryColor() = isBarPrimaryColor

    override fun initData() {
        val layoutManager = LinearLayoutManager(context)
        mBinding?.rvHome?.layoutManager = layoutManager
        listAdapter = ListAdapter(ArrayList())
        listAdapter?.setLoadMoreView(ExpandLoadMoewView())
        mBinding?.rvHome?.adapter = listAdapter
        listAdapter?.setEnableLoadMore(enableLoadMore())
        if (enableLoadMore()) {
            listAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    onLoadMore()
                }
            }, mBinding?.rvHome)
        }


        mBinding?.rvHome?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
//                if (distance < -ViewConfiguration.get(context).getScaledEdgeSlop() && !isAskVisible) {
//                    //显示
//                    showAskWithAnim();
//                    distance = 0;
//                    isAskVisible = true;
//                } else if (distance > ViewConfiguration.get(context).getScaledEdgeSlop() && isAskVisible) {
//                    //隐藏
//                    hideAskWithAnim();
//                    distance = 0;
//                    isAskVisible = false;
//                }
//                //向下滑并且可见 或者 向上滑并且不可见
//                if ((dy > 0 && isAskVisible) || (dy < 0 && !isAskVisible)) {
//                    distance += dy;
//                }

                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition <= 0) {
                    isBarPrimaryColor = false
                    headerBannerProvider?.startBanner()
                    (parentFragment as HomeFragment).setTopBackGroundColor(bannerColorRes)
                } else {
                    isBarPrimaryColor = true
                    headerBannerProvider?.pauseBanner()
                    (parentFragment as HomeFragment).setTopBackGroundColor(ContextCompat.getColor(context!!,R.color.colorPrimary))
                }

                if (firstVisibleItemPosition < 8) {
                    hideArrow()
                } else {
                    showArrow()
                }
            }
        })

        listAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view?.id) {
                R.id.iv_2369 -> {
                    jumpGoodsDetail("2369")
                }

                R.id.ll_classify -> {
                    EventBus.getDefault().post(BackClassifyEvent())
                }

                R.id.ll_ziyin -> {
                    jumpSearch("滋补保健")
                }

                R.id.ll_qingqu -> {
                    jumpSearch("情趣用品")
                }

            //精选优品 跳转商品详情
                R.id.iv_yp1 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 0)
                    startActivity(intent)
                }
                R.id.iv_yp2 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 1)
                    startActivity(intent)
                }
                R.id.iv_yp3 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 2)
                    startActivity(intent)
                }
                R.id.iv_yp4 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 3)
                    startActivity(intent)
                }
                R.id.iv_yp5 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 4)
                    startActivity(intent)
                }
                R.id.iv_yp6 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 5)
                    startActivity(intent)
                }
                R.id.iv_yp7 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 6)
                    startActivity(intent)
                }
                R.id.iv_yp8 -> {
                    val intent = Intent(context, BannerActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("position", 7)
                    startActivity(intent)
                }
                R.id.ll_yp9 -> {
                    jumpGoodsDetail("21536")
                }
                R.id.ll_yp10 -> {
                    jumpGoodsDetail("26727")
                }
                R.id.ll_yp11 -> {
                    jumpGoodsDetail("2094")
                }
                R.id.ll_yp12 -> {
                    jumpGoodsDetail("41259")
                }

            //以下是品牌跳转
                R.id.ll_brand_1 -> {
                    brandJump("4179", "杜蕾斯")
                }
                R.id.ll_brand_2 -> {
                    brandJump("4504", "汇仁")
                }
                R.id.ll_brand_3 -> {
                    brandJump("4324", "陈李济")
                }
                R.id.ll_brand_4 -> {
                    brandJump("4257", "汤臣倍健")
                }
                R.id.ll_brand_5 -> {
                    brandJump("4410", "东阿阿胶")
                }
                R.id.ll_brand_6 -> {
                    brandJump("4123", "罗浮山国药")
                }
                R.id.ll_brand_7 -> {
                    brandJump("4199", "九芝堂")
                }
                R.id.ll_brand_8 -> {
                    brandJump("4119", "同仁堂")
                }
                R.id.ll_brand_9 -> {
                    brandJump("4526", "999")
                }
                R.id.ll_brand_10 -> {
                    brandJump("6287", "三诺")
                }
                R.id.ll_brand_11 -> {
                    brandJump("7428", "碧生源")
                }
                R.id.ll_brand_12 -> {
                    brandJump("4258", "香雪制药")
                }


            //以下是疾病跳转
                R.id.ll_s_1 -> {
                    jumpSearch("无法勃起")
                }
                R.id.ll_s_2 -> {
                    jumpSearch("硬度不够")
                }
                R.id.ll_s_3 -> {
                    jumpSearch("早泄")
                }
                R.id.ll_s_4 -> {
                    jumpSearch("性欲退减")
                }
                R.id.ll_s_5 -> {
                    jumpSearch("遗精")
                }
                R.id.ll_s_6 -> {
                    jumpSearch("手淫过度")
                }
                R.id.ll_s_7 -> {
                    jumpSearch("性部位溃烂")
                }
                R.id.ll_s_8 -> {
                    jumpSearch("少精弱精")
                }


            //以下是男科分类点击的跳转
                R.id.ll_c_1 -> {
                    jumpGoodsDetail("2018")
                }
                R.id.ll_c_2 -> {
                    jumpGoodsDetail("372")
                }
                R.id.ll_c_3 -> {
                    jumpGoodsDetail("41297")
                }
                R.id.ll_c_4 -> {
                    jumpGoodsDetail("21536")
                }
                R.id.ll_c_5 -> {
                    jumpGoodsDetail("2223")
                }
                R.id.ll_c_6 -> {
                    jumpGoodsDetail("41259")
                }
                R.id.ll_c_7 -> {
                    jumpGoodsDetail("37597")
                }
                R.id.ll_c_8 -> {
                    jumpGoodsDetail("26727")
                }

            //以下是男性加油站跳转
                R.id.ll_man_station_1 -> {
                    jumpGoodsDetail("27158")
                }
                R.id.ll_man_station_2 -> {
                    jumpGoodsDetail("36096")
                }
                R.id.ll_man_station_3 -> {
                    jumpGoodsDetail("45425")
                }
                R.id.ll_man_station_4 -> {
                    jumpGoodsDetail("21536")
                }
                R.id.ll_man_station_5 -> {
                    jumpGoodsDetail("402")
                }
                R.id.ll_man_station_6 -> {
                    jumpGoodsDetail("373")
                }
                R.id.ll_man_station_7 -> {
                    jumpGoodsDetail("2018")
                }
            }
        }

        listAdapter?.setOnItemClickListener { adapter, view, position ->
            val entity = listAdapter?.data?.get(position)
            if (entity?.getItemType() == IHomeEntity.RECOMMEND) {
                val data = entity as HomeRecommendEntity.Data
                val intent = Intent(context, GoodsDetailActivity::class.java)
                intent?.putExtra(C.GOODS_INFO_ID, data?.goods_info_id)
                intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        mBinding?.ivTop?.setOnClickListener {
            mBinding?.rvHome?.scrollToPosition(0)
        }
    }


    fun getDefaultAlphaAnimation(`in`: Boolean): Animation {
        val alphaAnimation = AlphaAnimation(if (`in`) 0.5f else 1.0f, if (`in`) 1.0f else 0.5f)
        alphaAnimation.interpolator = AccelerateInterpolator()
        return alphaAnimation
    }

    /**
     * 显示问答按钮
     */
    protected fun showAskWithAnim() {
        val set = AnimationSet(false)
        val fromX = mBinding?.ivAsk?.width!! * 0.8f
        val shakeAnimaw = TranslateAnimation(fromX, 0f, 0f, 0f)
        set.duration = 300
        set.addAnimation(shakeAnimaw)
        set.addAnimation(getDefaultAlphaAnimation(true))
        set.fillAfter = true
        mBinding?.ivAsk?.startAnimation(set)
    }

    /**
     * 隐藏问答按钮
     */
    protected fun hideAskWithAnim() {
        val set = AnimationSet(false)
        val toX = mBinding?.ivAsk?.width!! * 0.8f
        val shakeAnimaw = TranslateAnimation(0f, toX, 0f, 0f)
        set.duration = 300
        set.addAnimation(shakeAnimaw)
        set.addAnimation(getDefaultAlphaAnimation(false))
        set.fillAfter = true
        mBinding?.ivAsk?.startAnimation(set)
    }

    /**
     * 跳转商品详情
     */
    protected fun jumpGoodsDetail(goodsInfoId: String) {
        val intent = Intent(context, GoodsDetailActivity::class.java)
        intent?.putExtra(C.GOODS_INFO_ID, goodsInfoId)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    /**
     * 搜索跳转
     */
    protected fun jumpSearch(keywords: String) {
        val intent = Intent(context, SearchActivity::class.java)
        intent.putExtra("keywords", keywords)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    /**
     * 品牌跳转
     */
    protected fun brandJump(brandID: String, title: String) {
        val intent = Intent(context, SearchWithIdActivity::class.java)
        intent.putExtra("brandID", brandID)
        intent.putExtra("title", title)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    protected abstract fun useRoundedCorners(): Boolean


    protected fun showArrow() {
        if (arrowVisiable == false) {
            mBinding?.ivTop?.visibility = View.VISIBLE
//            showArrowAnimation(mBinding?.ivTop)
            arrowVisiable = true
        }
    }

    protected fun hideArrow() {
        if (arrowVisiable) {
            mBinding?.ivTop?.visibility = View.INVISIBLE
//            hideArrowAnimation(mBinding?.ivTop)
            arrowVisiable = false
        }
    }

    fun showArrowAnimation(view: View?) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 1f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }

    fun hideArrowAnimation(view: View?) {
        val pvhX = PropertyValuesHolder.ofFloat("alpha", 0f)
        val pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f)
        val pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f)
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(200).start()
    }

    override fun onSupportInvisible() {
        pauseBanner()
        super.onSupportInvisible()
    }

    fun pauseBanner() {
        headerBannerProvider?.pauseBanner()
    }

    fun startBanner() {
        if (firstVisibleItemPosition == 0) {
            headerBannerProvider?.startBanner()
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        startBanner()
    }

    open fun enableLoadMore(): Boolean {
        return true
    }

    protected open fun registerItemProvider(providerDelegate: ProviderDelegate) {
        //头部广告
        headerBannerProvider = HeaderBannerProvider(useRoundedCorners())
        headerBannerProvider?.setOnBannerPageChangeListener(object : HeaderBannerProvider.OnBannerPageChangeListener {
            override fun onBannerPageSelected(position: Int?, colorRes: Int?) {
                setBackGroundColor(colorRes)
            }
        })
        providerDelegate.registerProvider(headerBannerProvider)
    }

    abstract fun onLoadMore()

    abstract fun setBackGroundColor(colorRes: Int?)

    fun finishLoadMore(datas: MutableList<HomeRecommendEntity.Data>) {
        listAdapter?.addData(datas)
        if (datas?.size < C.PAGE_SIZE) {
            listAdapter?.loadMoreEnd()
        } else {
            listAdapter?.loadMoreComplete()
        }
    }


    /**
     * 列表数据
     */
    fun setListDatas(datas: MutableList<IHomeEntity>) {
        listAdapter?.setNewData(datas)
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    inner class ListAdapter
    constructor(datas: MutableList<IHomeEntity>)
        : MultipleItemRvAdapter<IHomeEntity, BaseViewHolder>(datas) {

        init {
            finishInitialize()
        }

        override fun registerItemProvider() {
            this@BaseHomeChildListFragment.registerItemProvider(mProviderDelegate)
        }

        override fun getViewType(homeEntity: IHomeEntity): Int {
            return homeEntity.getItemType()
        }

    }
}