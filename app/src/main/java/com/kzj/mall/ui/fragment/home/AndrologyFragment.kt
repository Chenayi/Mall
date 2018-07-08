package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.*
import com.kzj.mall.entity.home.*

/**
 * 男科
 */
class AndrologyFragment : BaseHomeChildListFragment<IPresenter>() {
    var headerBannerProvider: HeaderBannerProvider? = null

    companion object {
        fun newInstance(): AndrologyFragment {
            val andrologyFragment = AndrologyFragment()
            return andrologyFragment
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
        providerDelegate.registerProvider(AndrologyClassifyProvider())
        providerDelegate.registerProvider(AndrologyStationProvider())
        providerDelegate.registerProvider(AndrologyAdvBannerProvider())
        providerDelegate.registerProvider(AndroilogyBrandProvider())
        providerDelegate.registerProvider(AndrologySpecialFieldProvider())
        providerDelegate.registerProvider(RecommendProvider())
    }

    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(HomeHeaderBannerEntity())
        list.add(AndrologyClassifyEntity())
        list.add(AndrologyStationEntity())

        //穿插广告
        list.add(DataHelper.andrologyAdvBannerData())

        //品牌
        list.add(AndrologyBrandEntity())

        // 专场
        list.add(DataHelper.andrologySpecialFieldData())
        list.add(DataHelper.andrologySpecialFieldData())
        return list
    }

    override fun onLoadMore() {
        finishLoadMore(DataHelper.homeRecommendDatas())
    }
}