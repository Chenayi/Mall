package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.C
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

/**
 * 男科
 */
class AndrologyFragment : BaseHomeChildListFragment() {
    private var pageNo = 0

    companion object {
        fun newInstance(): AndrologyFragment {
            val andrologyFragment = AndrologyFragment()
            return andrologyFragment
        }
    }

    override fun initData() {
        super.initData()
        setListDatas(ArrayList())

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

        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestHomeDatas()
        }

        mPresenter?.requestAndrologyDatas()
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
        pageNo = 0
        mBinding?.refreshLayout?.isRefreshing = false
        setListDatas(getNormalMultipleEntities())
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
        mBinding?.refreshLayout?.isRefreshing = false
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

    override fun onLoadMore() {
        pageNo += 1
        mPresenter?.loadRecommendDatas(pageNo, C.PAGE_SIZE)
    }
}