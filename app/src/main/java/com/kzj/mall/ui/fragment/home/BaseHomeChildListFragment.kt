package com.kzj.mall.ui.fragment.home

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentBaseHomeChildListBinding
import com.kzj.mall.entity.home.HomeRecommendEntity
import com.kzj.mall.entity.home.IHomeEntity

abstract class BaseHomeChildListFragment<P : IPresenter> : BaseFragment<P, FragmentBaseHomeChildListBinding>() {
    private var listAdapter: ListAdapter? = null
    private var headerBannerView: View? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_home_child_list
    }

    override fun initData() {
        mBinding?.refreshLayout?.isEnabled = false

        val layoutManager = LinearLayoutManager(context)
        layoutManager.recycleChildrenOnDetach = true
        mBinding?.rvHome?.layoutManager = layoutManager
        listAdapter = ListAdapter(ArrayList())
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

    open fun enableLoadMore(): Boolean {
        return true
    }

    abstract fun registerItemProvider(providerDelegate: ProviderDelegate)
    abstract fun onLoadMore()

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