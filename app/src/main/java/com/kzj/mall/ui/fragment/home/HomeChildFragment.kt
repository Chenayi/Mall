package com.kzj.mall.ui.fragment.home

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.SexToyEntity
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

class HomeChildFragment : BaseHomeChildListFragment() {

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
        mBinding?.refreshLayout?.setOnRefreshListener {
            mPresenter?.requestHomeDatas()
        }
        mPresenter?.requestHomeDatas()
    }

    override fun initImmersionBar() {
        if (!isBarPrimaryColor()){
            (parentFragment as HomeFragment).setTopBackGroundColor(bannerColorRes)
        }else{
            (parentFragment as HomeFragment).setTopBackGroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        }
    }

    override fun setBackGroundColor(colorRes: Int?) {
        (parentFragment as HomeFragment)?.setTopBackGroundColor(colorRes)
        bannerColorRes = colorRes
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
//        val homeHeaderBannerEntity = HomeHeaderBannerEntity()
//        homeHeaderBannerEntity.adss = homeEntity?.adss
        headerBannerProvider?.refresh = true
        datas.add(LocalDatas.homeBannerData())

        //分类
        datas.add(HomeClassifyEntity())

        //公告精选
        homeEntity?.promotionalAd?.let {
            val homeChoiceEntity = HomeChoiceEntity()
            homeChoiceEntity?.promotionalAd = it
            datas.add(homeChoiceEntity)
        }

        //每日闪购
        homeEntity?.dailyBuy?.let {
            if (it?.size > 0) {
                val homeFlashSaleEntity = HomeFlashSaleEntity()
                homeFlashSaleEntity?.dailyBuy = homeEntity?.dailyBuy
                datas.add(homeFlashSaleEntity)
            }
        }


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
//        val homeAskAnswerEntity = HomeAskAnswerEntity()
//        homeAskAnswerEntity?.askList = homeEntity?.askList
//        datas.add(homeAskAnswerEntity)

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