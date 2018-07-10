package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.adapter.provider.home.*
import com.kzj.mall.base.IPresenter
import com.kzj.mall.entity.home.*
import com.kzj.mall.utils.LocalDatas

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
        //头部广告
        headerBannerProvider = HeaderBannerProvider()
        providerDelegate.registerProvider(headerBannerProvider)
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
        list.add(HomeHeaderBannerEntity())
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