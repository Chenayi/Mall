package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.*
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.C
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.SexToyEntity
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

class HomeChildFragment : BaseHomeChildListFragment() {
    private var isAskVisible = true
    private var distance = 0
    private var pageNo = 0

    companion object {
        fun newInstance(): HomeChildFragment {
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
        }
    }

    override fun useRoundedCorners() = true

    override fun initData() {
        super.initData()
        mBinding?.ivAsk?.visibility = View.VISIBLE
        mBinding?.rvHome?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (distance < -ViewConfiguration.get(context).getScaledEdgeSlop() && !isAskVisible) {
                    //显示
                    showAskWithAnim();
                    distance = 0;
                    isAskVisible = true;
                } else if (distance > ViewConfiguration.get(context).getScaledEdgeSlop() && isAskVisible) {
                    //隐藏
                    hideAskWithAnim();
                    distance = 0;
                    isAskVisible = false;
                }
                //向下滑并且可见 或者 向上滑并且不可见
                if ((dy > 0 && isAskVisible) || (dy < 0 && !isAskVisible)) {
                    distance += dy;
                }

                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager?.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition == 0) {
                    headerBannerProvider?.startBanner()
                } else {
                    headerBannerProvider?.pauseBanner()
                }
                if (firstVisibleItemPosition < 8){
                    hideArrow()
                }else{
                    showArrow()
                }
            }
        })

        mBinding?.refreshLayout?.isEnabled = true
        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestHomeDatas()
        }


        mPresenter?.requestHomeDatas()
    }

    fun getDefaultAlphaAnimation(`in`: Boolean): Animation {
        val alphaAnimation = AlphaAnimation(if (`in`) 0.5f else 1.0f, if (`in`) 1.0f else 0.5f)
        alphaAnimation.interpolator = AccelerateInterpolator()
        return alphaAnimation
    }

    /**
     * 显示问答按钮
     */
    private fun showAskWithAnim() {
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
    private fun hideAskWithAnim() {
        val set = AnimationSet(false)
        val toX = mBinding?.ivAsk?.width!! * 0.8f
        val shakeAnimaw = TranslateAnimation(0f, toX, 0f, 0f)
        set.duration = 300
        set.addAnimation(shakeAnimaw)
        set.addAnimation(getDefaultAlphaAnimation(false))
        set.fillAfter = true
        mBinding?.ivAsk?.startAnimation(set)
    }

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
        super.registerItemProvider(providerDelegate)
        //分类
        providerDelegate.registerProvider(HomeClassifyProvider())
        //公告精选
        providerDelegate.registerProvider(HomeChoiceProvider())
        //每日闪购
        providerDelegate.registerProvider(HomeFlashSaleProvider())
        //品牌
        providerDelegate.registerProvider(HomeBrandProvider())
        //精选优品
        providerDelegate.registerProvider(HomeChoiceGoodsProvider())
        //穿插广告
        providerDelegate.registerProvider(HomeAdvBannerProvider())
        //常见疾病
        providerDelegate.registerProvider(HomeSicknessProvider())
        //情趣用品
        providerDelegate.registerProvider(HomeSexToyProvider())
        //问答
        providerDelegate.registerProvider(HomeAskAnswerProvider())
        //推荐
        providerDelegate.registerProvider(RecommendProvider())
    }

    override fun showHomeDatas(homeEntity: HomeEntity?) {
        pageNo = 0

        mBinding?.refreshLayout?.isRefreshing = false

        val datas = ArrayList<IHomeEntity>()

        //广告
        val homeHeaderBannerEntity = HomeHeaderBannerEntity()
        homeHeaderBannerEntity.adss = homeEntity?.adss
        datas.add(homeHeaderBannerEntity)

        //分类
        datas.add(HomeClassifyEntity())

        //公告精选
        val homeChoiceEntity = HomeChoiceEntity()
        homeChoiceEntity?.promotionalAd = homeEntity?.promotionalAd
        datas.add(homeChoiceEntity)

        //每日闪购
        val homeFlashSaleEntity = HomeFlashSaleEntity()
        homeFlashSaleEntity?.dailyBuy = homeEntity?.dailyBuy
        datas.add(homeFlashSaleEntity)

        //精选优品
        datas.add(HomeChoiceGoodsEntity())

        //穿插广告
        datas.add(LocalDatas.homeAdvBannerData())

        //常见疾病
        datas.add(HomeSicknessEntity())

        //品牌专区
        datas.add(HomeBrandEntity())

        //情趣用品
        val sexToyEntity = SexToyEntity()
        sexToyEntity?.qingqu = homeEntity?.qingqu
        datas.add(sexToyEntity)

        //问答解惑
        val homeAskAnswerEntity = HomeAskAnswerEntity()
        homeAskAnswerEntity?.askList = homeEntity?.askList
        datas.add(homeAskAnswerEntity)

        setListDatas(datas)
    }

    override fun onLoadMore() {
        pageNo += 1
        mPresenter?.loadRecommendDatas(pageNo, C.PAGE_SIZE)

    }

    override fun loadRecommendDatas(homeRecommendEntity: HomeRecommendEntity?) {
        homeRecommendEntity?.results?.data?.let {
            if (pageNo == 1) {
                it?.get(0)?.isShowRecommendText = true
            }
            for (i in 0 until it.size) {
                it?.get(i)?.isBackgroundCorners = true
            }
            finishLoadMore(it)
        }
    }
}