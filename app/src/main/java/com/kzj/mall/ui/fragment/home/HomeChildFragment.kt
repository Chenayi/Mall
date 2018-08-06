package com.kzj.mall.ui.fragment.home

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.*
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

class HomeChildFragment : BaseHomeChildListFragment() {
    private var isAskVisible = true
    private var distance = 0

    companion object {
        fun newInstance(): HomeChildFragment {
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
        }
    }

    override fun backgroundColor(): Int {
        return ContextCompat.getColor(context!!,R.color.colorPrimary)
    }

    override fun initData() {
        super.initData()
        mBinding?.ivAsk?.visibility = View.VISIBLE

        setListDatas(getNormalMultipleEntities())

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
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView?.layoutManager
                if (layoutManager is LinearLayoutManager) {
                    val linearLayoutManager: LinearLayoutManager = layoutManager
                    val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    if (firstVisibleItemPosition == 0) {
                        headerBannerProvider?.startBanner()
                    } else {
                        headerBannerProvider?.pauseBanner()
                    }
                }
            }
        })


        mPresenter?.requestHomeDatas()
    }

    private fun hideAskWithAnim() {
        val set = AnimationSet(false)
        val toX = mBinding?.ivAsk?.width!! * 0.7f
        val shakeAnimaw = TranslateAnimation(0f, toX, 0f, 0f)
        set.duration = 300
        set.addAnimation(shakeAnimaw)
        set.addAnimation(getDefaultAlphaAnimation(false))
        set.fillAfter = true
        mBinding?.ivAsk?.startAnimation(set)
    }

    fun getDefaultAlphaAnimation(`in`: Boolean): Animation {
        val alphaAnimation = AlphaAnimation(if (`in`) 0.5f else 1.0f, if (`in`) 1.0f else 0.5f)
        alphaAnimation.interpolator = AccelerateInterpolator()
        return alphaAnimation
    }

    private fun showAskWithAnim() {
        val set = AnimationSet(false)
        val fromX = mBinding?.ivAsk?.width!! * 0.7f
        val shakeAnimaw = TranslateAnimation(fromX, 0f, 0f, 0f)
        set.duration = 300
        set.addAnimation(shakeAnimaw)
        set.addAnimation(getDefaultAlphaAnimation(true))
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

    override fun onLoadMore() {
        finishLoadMore(getRecommendDatas())
    }

    /**
     * 为您推荐
     */
    fun getRecommendDatas(): MutableList<HomeRecommendEntity> {
        return LocalDatas.homeRecommendDatas()
    }


    /**
     * 列表数据
     */
    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        //头部广告
        list.add(LocalDatas.homeBannerData())
        //分类
        list.add(HomeClassifyEntity())
        //公告精选
        list.add(HomeChoiceEntity())
        //每日闪购
        list.add(LocalDatas.homeFlashData())
        //精选优品
        list.add(HomeChoiceGoodsEntity())
        //穿插广告
        list.add(LocalDatas.homeAdvBannerData())
        //常见疾病
        list.add(HomeSicknessEntity())
        //品牌专区
        list.add(HomeBrandEntity())
        //情趣用品
        list.add(LocalDatas.homeSexToy())
        //问答解惑
        list.add(HomeAskAnswerEntity())
        //穿插广告
        list.add(LocalDatas.homeAdvBannerData())
        return list
    }
}