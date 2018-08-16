package com.kzj.mall.ui.fragment.home

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.adapter.provider.home.HeaderBannerProvider
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentBaseHomeChildListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerHomeComponent
import com.kzj.mall.di.module.HomeModule
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.mvp.contract.HomeContract
import com.kzj.mall.mvp.presenter.HomePresenter
import com.kzj.mall.widget.ExpandLoadMoewView

abstract class BaseHomeChildListFragment : BaseFragment<HomePresenter, FragmentBaseHomeChildListBinding>(), HomeContract.View {
    private var listAdapter: ListAdapter? = null
    protected var headerBannerProvider: HeaderBannerProvider? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_base_home_child_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(HomeModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mBinding?.refreshLayout?.isEnabled = false
        val layoutManager = LinearLayoutManager(context)
        mBinding?.rvHome?.layoutManager = layoutManager
        listAdapter = ListAdapter(ArrayList())
        listAdapter?.setLoadMoreView(ExpandLoadMoewView())
        mBinding?.rvHome?.adapter = listAdapter
        listAdapter?.setEnableLoadMore(enableLoadMore())
        if (enableLoadMore()) {
            listAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    onLoadMore()
                }
            }, mBinding?.rvHome)
        }
    }


    override fun onSupportInvisible() {
        headerBannerProvider?.pauseBanner()
        super.onSupportInvisible()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        headerBannerProvider?.startBanner()
    }

    open fun enableLoadMore(): Boolean {
        return true
    }

    protected open fun registerItemProvider(providerDelegate: ProviderDelegate) {
        //头部广告
        headerBannerProvider = HeaderBannerProvider(false)
        headerBannerProvider?.setOnBannerPageChangeListener(object : HeaderBannerProvider.OnBannerPageChangeListener {
            override fun onBannerPageSelected(position: Int?, colorRes: Int?) {
                setBackGroundColor(colorRes)
            }
        })
        providerDelegate.registerProvider(headerBannerProvider)
    }

    abstract fun onLoadMore()

    private var backgroundColor: Int?=null

    fun setBackGroundColor(colorRes: Int?) {
        colorRes?.let {
            backgroundColor = it
            (parentFragment as HomeFragment)?.setBackGroundColor(it)
        }
    }

    fun changeBackgroundColor() {
        backgroundColor?.let {
            (parentFragment as HomeFragment)?.setBackGroundColor(backgroundColor)
        }
    }

    fun finishLoadMore(datas: MutableList<HomeRecommendEntity>) {
        listAdapter?.addData(datas)
        listAdapter?.loadMoreEnd()
    }


    /**
     * 列表数据
     */
    fun setListDatas(datas: MutableList<IHomeEntity>) {
        listAdapter?.setNewData(datas)
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    inner class ListAdapter
    constructor(datas: MutableList<IHomeEntity>)
        : MultipleItemRvAdapter<IHomeEntity, BaseViewHolder>(datas) {

        init {
            finishInitialize()
        }

        override fun registerItemProvider() {
            this@BaseHomeChildListFragment.registerItemProvider(mProviderDelegate)
        }

        override fun getViewType(homeEntity: IHomeEntity): Int {
            return homeEntity.getItemType()
        }

    }
}