package com.kzj.mall.ui.fragment.home

import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.HomeRecommendEntity
import com.kzj.mall.entity.IHomeEntity

class HomeChildFragment : BaseHomeChildListFragment<IPresenter>() {

    companion object {
        fun newInstance(): HomeChildFragment {
            val homeChildFragment = HomeChildFragment()
            return homeChildFragment
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

    fun getRecommendDatas():MutableList<IHomeEntity>{
        val list = ArrayList<IHomeEntity>()
        for (i in 0..8){
            val homeRecommendEntity = HomeRecommendEntity()
            homeRecommendEntity.isBackgroundCorners = true
            if (i == 0){
                homeRecommendEntity.isShowRecommendText = true
            }else{
                homeRecommendEntity.isShowRecommendText = false
            }
            list.add(homeRecommendEntity)
        }
        return list
    }


    fun getNormalMultipleEntities(): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(HomeEntity(IHomeEntity.CLASSIFY))
        list.add(HomeEntity(IHomeEntity.CHOICE))
        list.add(HomeEntity(IHomeEntity.FLASH_SALE))
        list.add(HomeEntity(IHomeEntity.CHOICE_GOODS))
        list.add(HomeEntity(IHomeEntity.ADV_BANNER))
        list.add(HomeEntity(IHomeEntity.SICKNESS))
        list.add(HomeEntity(IHomeEntity.BRAND))
        list.add(HomeEntity(IHomeEntity.SEX_TOY))
        list.add(HomeEntity(IHomeEntity.ASK_ANSWER))
        return list
    }
}