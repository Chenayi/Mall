package com.kzj.mall.ui.fragment.home

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.util.ProviderDelegate
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.entity.AndrologySpecialFieldEntity
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
        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestAndrologyDatas()
        }

        mPresenter?.requestAndrologyDatas()
    }

    override fun setBackGroundColor(colorRes: Int?) {
        (parentFragment as HomeFragment)?.setTopBackGroundColor(colorRes)
        bannerColorRes = colorRes
    }

    override fun useRoundedCorners() = false

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
        super.registerItemProvider(providerDelegate)
        providerDelegate.registerProvider(AndrologyClassifyProvider())
        providerDelegate.registerProvider(AndrologyStationProvider())
        providerDelegate.registerProvider(AndrologyAdvBannerProvider())
        providerDelegate.registerProvider(AndroilogyBrandProvider())
        providerDelegate.registerProvider(AndrologySpecialFieldProvider())
        providerDelegate.registerProvider(RecommendTextProvider())
        providerDelegate.registerProvider(RecommendProvider())
    }

    override fun showHomeDatas(homeEntity: HomeEntity?) {
        pageNo = 0
        mBinding?.refreshLayout?.isRefreshing = false
        headerBannerProvider?.refresh = true
        setListDatas(getNormalMultipleEntities(homeEntity))
    }

    fun getNormalMultipleEntities(homeEntity: HomeEntity?): MutableList<IHomeEntity> {
        val list = ArrayList<IHomeEntity>()
        list.add(LocalDatas.manBannerData())
        list.add(AndrologyClassifyEntity())
        list.add(AndrologyStationEntity())

        //穿插广告
        list.add(LocalDatas.andrologyAdvBannerData())

        //品牌
        list.add(AndrologyBrandEntity())

        //亲热
        homeEntity?.qinre?.let {
            if (it?.size > 0) {
                var andrologySpecialFieldEntity2 = AndrologySpecialFieldEntity()
                andrologySpecialFieldEntity2?.specialFields = homeEntity?.qinre
                andrologySpecialFieldEntity2.type = AndrologySpecialFieldEntity.TYPE_QINRE
                list.add(andrologySpecialFieldEntity2)
            }
        }

        //滋补
        homeEntity?.zibu?.let {
            if (it?.size > 0) {
                var andrologySpecialFieldEntity1 = AndrologySpecialFieldEntity()
                andrologySpecialFieldEntity1?.specialFields = homeEntity?.zibu
                andrologySpecialFieldEntity1.type = AndrologySpecialFieldEntity.TYPE_ZIBU
                list.add(andrologySpecialFieldEntity1)
            }
        }

        //推荐文本
        list.add(HomeRecommendTextEntity())

        return list
    }

    override fun loadRecommendDatas(homeRecommendEntity: HomeRecommendEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        homeRecommendEntity?.results?.data?.let {
            for (i in 0 until it.size) {
                it?.get(i)?.isBackgroundCorners = false

                if (i % 2 == 0) {
                    it.get(i).isShowRightMargin = false
                    it?.get(i).isShowRightMargin2 = true
                    it.get(i).isShowLeftMargin = true
                    it.get(i).isShowLeftMargin2 = false
                } else {
                    it.get(i).isShowRightMargin = true
                    it?.get(i).isShowRightMargin2 = false
                    it.get(i).isShowLeftMargin = false
                    it.get(i).isShowLeftMargin2 = true
                }

                if ((i == 0 || i == 1) && pageNo <= 1) {
                    it.get(i).isShowTopMargin = false
                }else{
                    it.get(i).isShowTopMargin = true
                }
            }
            finishLoadMore(it)
        }
    }

    override fun onLoadMore() {
        pageNo += 1
        mPresenter?.loadRecommendDatas(pageNo, C.PAGE_SIZE)
    }
}