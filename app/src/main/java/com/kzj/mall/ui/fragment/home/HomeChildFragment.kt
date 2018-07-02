package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.*

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
        providerDelegate.registerProvider(ClassifyProvider())
        providerDelegate.registerProvider(ChoiceProvider())
        providerDelegate.registerProvider(BrandProvider())
        providerDelegate.registerProvider(FlashSaleProvider())
        providerDelegate.registerProvider(ChoiceGoodsProvider())
        providerDelegate.registerProvider(AdvBannerProvider())
        providerDelegate.registerProvider(SicknessProvider())
        providerDelegate.registerProvider(SexToyProvider())
        providerDelegate.registerProvider(AskAnswerProvider())
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
        list.add(HomeEntity(IHomeEntity.CLASSIFY))
        list.add(HomeEntity(IHomeEntity.CHOICE))
        list.add(HomeEntity(IHomeEntity.FLASH_SALE))
        list.add(HomeEntity(IHomeEntity.CHOICE_GOODS))
        list.add(HomeEntity(IHomeEntity.ADV_BANNER))
        list.add(HomeEntity(IHomeEntity.SICKNESS))
        list.add(HomeEntity(IHomeEntity.BRAND))
        list.add(SexToyEntity())
        list.add(HomeEntity(IHomeEntity.ASK_ANSWER))
        return list
    }
}