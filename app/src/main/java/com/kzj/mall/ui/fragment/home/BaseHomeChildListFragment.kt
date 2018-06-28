package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.*
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentBaseHomeChildListBinding
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.IHomeEntity
import com.kzj.mall.widget.HomeBanner

abstract class BaseHomeChildListFragment<P : IPresenter> : BaseFragment<P, FragmentBaseHomeChildListBinding>() {
    private var listAdapter: ListAdapter? = null
    private var headerBannerView: View? = null
    private var banner: HomeBanner? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_home_child_list
    }

    override fun initData() {
        mBinding?.refreshLayout?.isEnabled = false
        listAdapter = ListAdapter(ArrayList())
        mBinding?.rvHome?.layoutManager = LinearLayoutManager(context)
        mBinding?.rvHome?.adapter = listAdapter
        addHeaderView()
        listAdapter?.setEnableLoadMore(enableLoadMore())
        if (enableLoadMore()){
            listAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener{
                override fun onLoadMoreRequested() {
                    onLoadMore()
                }
            },mBinding?.rvHome)
        }
    }

    open fun enableLoadMore(): Boolean {
        return false
    }

    abstract fun onLoadMore()

    fun finishLoadMore(datas : MutableList<IHomeEntity>){
        listAdapter?.addData(datas)
        listAdapter?.loadMoreEnd()
    }

    fun addHeaderView() {
        headerBannerView = layoutInflater.inflate(R.layout.header_home_banner, mBinding?.rvHome?.parent as ViewGroup, false)
        banner = headerBannerView?.findViewById(R.id.home_banner)
        listAdapter?.addHeaderView(headerBannerView)
    }

    /**
     * 广告数据
     */
    fun setBannerData(banners: MutableList<String>) {
        banner?.setBanners(banners)
    }

    /**
     * 列表数据
     */
    fun setListDatas(datas: MutableList<IHomeEntity>) {
        listAdapter?.setNewData(datas)
    }

    fun setBannerPadding(left: Int, top: Int, right: Int, bottom: Int) {
        banner?.setBannerPadding(left, top, right, bottom)
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        banner?.start()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        banner?.pause()
    }

    inner class ListAdapter
    constructor(datas: MutableList<IHomeEntity>)
        : MultipleItemRvAdapter<IHomeEntity, BaseViewHolder>(datas) {

        init {
            finishInitialize()
        }

        override fun registerItemProvider() {
            mProviderDelegate.registerProvider(ClassifyProvider())
            mProviderDelegate.registerProvider(ChoiceProvider())
            mProviderDelegate.registerProvider(BrandProvider())
            mProviderDelegate.registerProvider(FlashSaleProvider())
            mProviderDelegate.registerProvider(ChoiceGoodsProvider())
            mProviderDelegate.registerProvider(AdvBannerProvider())
            mProviderDelegate.registerProvider(SicknessProvider())
            mProviderDelegate.registerProvider(SexToyProvider())
            mProviderDelegate.registerProvider(AskAnswerProvider())
            mProviderDelegate.registerProvider(RecommendProvider())
        }

        override fun getViewType(homeEntity: IHomeEntity): Int {
            return homeEntity.getItemType()
        }

    }
}