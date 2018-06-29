package com.kzj.mall.ui.fragment.home

import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.AndrologySpecialFieldEntity
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.HomeRecommendEntity
import com.kzj.mall.entity.IHomeEntity

/**
 * 男科
 */
class AndrologyFragment : BaseHomeChildListFragment<IPresenter>() {

    companion object {
        fun newInstance(): AndrologyFragment {
            val andrologyFragment = AndrologyFragment()
            return andrologyFragment
        }
    }

    override fun initData() {
        super.initData()
        setBannerPadding(0, SizeUtils.dp2px(18f), 0, 0)

        var banners = ArrayList<String>()
        for (i in 0 until 4) {
            banners.add("")
        }
        setBannerData(banners)

        setListDatas(getNormalMultipleEntities())
    }

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
        providerDelegate.registerProvider(AndrologyClassifyProvider())
        providerDelegate.registerProvider(AndrologyStationProvider())
        providerDelegate.registerProvider(AndrologyAdvBannerProvider())
        providerDelegate.registerProvider(AndrologySpecialFieldProvider())
        providerDelegate.registerProvider(RecommendProvider())
    }

    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(HomeEntity(IHomeEntity.CLASSIFY))
        list.add(HomeEntity(IHomeEntity.MALE_STATION))
        list.add(HomeEntity(IHomeEntity.ADV_BANNER))
        for (i in 0..2) {
            list.add(AndrologySpecialFieldEntity())
        }
        return list
    }

    override fun onLoadMore() {
        finishLoadMore(getRecommendDatas())
    }

    fun getRecommendDatas(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        for (i in 0..8) {
            val homeRecommendEntity = HomeRecommendEntity()
            homeRecommendEntity.isBackgroundCorners = false
            if (i == 0) {
                homeRecommendEntity.isShowRecommendText = true
            } else {
                homeRecommendEntity.isShowRecommendText = false
            }
            list.add(homeRecommendEntity)
        }
        return list
    }
}