package com.kzj.mall.ui.fragment.home

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

/**
 * 男科
 */
class AndrologyFragment : BaseHomeChildListFragment() {
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

    override fun useRoundedCorners()=false

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
        super.registerItemProvider(providerDelegate)
        providerDelegate.registerProvider(AndrologyClassifyProvider())
        providerDelegate.registerProvider(AndrologyStationProvider())
        providerDelegate.registerProvider(AndrologyAdvBannerProvider())
        providerDelegate.registerProvider(AndroilogyBrandProvider())
        providerDelegate.registerProvider(AndrologySpecialFieldProvider())
        providerDelegate.registerProvider(RecommendProvider())
    }

    override fun showHomeDatas(homeEntity: HomeEntity?) {

    }

    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(LocalDatas.homeBannerData())
        list.add(AndrologyClassifyEntity())
        list.add(AndrologyStationEntity())

        //穿插广告
        list.add(LocalDatas.andrologyAdvBannerData())

        //品牌
        list.add(AndrologyBrandEntity())

        // 专场
        list.add(LocalDatas.andrologySpecialFieldData())
        list.add(LocalDatas.andrologySpecialFieldData())
        return list
    }

    override fun loadRecommendDatas(homeRecommendEntity: HomeRecommendEntity?) {
    }

    override fun onLoadMore() {
        finishLoadMore(LocalDatas.homeRecommendDatas())
    }
}