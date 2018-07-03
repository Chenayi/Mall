package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.*
import com.kzj.mall.entity.home.*

class HomeChildFragment : BaseHomeChildListFragment<IPresenter>() {

    var headerBannerProvider: HeaderBannerProvider? = null

    companion object {
        fun newInstance(): HomeChildFragment {
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
        }
    }

    override fun initData() {
        super.initData()
        setListDatas(getNormalMultipleEntities())

        mBinding?.rvHome?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        headerBannerProvider?.pauseBanner()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        headerBannerProvider?.startBanner()
    }

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
        headerBannerProvider = HeaderBannerProvider()
        providerDelegate.registerProvider(headerBannerProvider)
        providerDelegate.registerProvider(HomeClassifyProvider())
        providerDelegate.registerProvider(HomeChoiceProvider())
        providerDelegate.registerProvider(HomeFlashSaleProvider())
        providerDelegate.registerProvider(HomeBrandProvider())
        providerDelegate.registerProvider(HomeChoiceGoodsProvider())
        providerDelegate.registerProvider(HomeAdvBannerProvider())
        providerDelegate.registerProvider(HomeSicknessProvider())
        providerDelegate.registerProvider(HomeSexToyProvider())
        providerDelegate.registerProvider(HomeAskAnswerProvider())
        providerDelegate.registerProvider(RecommendProvider())
    }

    override fun onLoadMore() {
        finishLoadMore(getRecommendDatas())
    }

    fun getRecommendDatas(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        for (i in 0..8) {
            val homeRecommendEntity = HomeRecommendEntity()
            homeRecommendEntity.isBackgroundCorners = true
            if (i == 0) {
                homeRecommendEntity.isShowRecommendText = true
            } else {
                homeRecommendEntity.isShowRecommendText = false
            }
            list.add(homeRecommendEntity)
        }
        return list
    }


    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(HomeHeaderBannerEntity())
        list.add(HomeClassifyEntity())
        list.add(HomeChoiceEntity())
        //每日闪购
        list.add(DataHelper.flashData())
        list.add(HomeChoiceGoodsEntity())
        list.add(HomeAdvBannerEntity())
        list.add(HomeSicknessEntity())
        list.add(HomeBrandEntity())
        list.add(SexToyEntity())
        list.add(HomeAskAnswerEntity())
        return list
    }
}