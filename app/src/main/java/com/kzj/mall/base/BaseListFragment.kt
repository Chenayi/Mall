package com.kzj.mall.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.databinding.FragmentBaseListBinding

abstract class BaseListFragment<P : IPresenter, D> : BaseFragment<P, FragmentBaseListBinding>() {
    private var listAdapter: ListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_list
    }

    override fun initData() {
        listAdapter = ListAdapter(ArrayList())
        mBinding?.rv?.layoutManager = layoutManager()
        mBinding?.rv?.adapter = listAdapter
        listAdapter?.setEnableLoadMore(isLoadMoreEnable())
        if (isLoadMoreEnable()) {
            listAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
                override fun onLoadMoreRequested() {
                    this@BaseListFragment.onLoadMore()
                }
            }, mBinding?.rv)
        }

        mBinding?.refreshLayout?.isEnabled = refreshEnable()
        mBinding?.refreshLayout?.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                this@BaseListFragment.onRefresh()
            }
        })
    }

    /**
     * holder
     */
    protected abstract fun myHolder(helper: BaseViewHolder?, data: D)

    /**
     * item
     */
    protected abstract fun itemLayout(): Int

    /**
     * 下拉刷新
     */
    protected abstract fun onRefresh();

    /**
     * 加载更多
     */
    protected abstract fun onLoadMore();

    /**
     * 加载更多开关
     */
    protected fun isLoadMoreEnable(): Boolean {
        return true
    }

    /**
     * 下拉刷新开关
     */
    protected fun refreshEnable(): Boolean {
        return true
    }

    /**
     * 刷新完成
     */
    protected fun finishRefresh(datas: MutableList<D>) {
        mBinding?.refreshLayout?.isRefreshing = false
        listAdapter?.setNewData(datas)
        if (isLoadMoreEnable() && datas?.size < C.PAGE_SIZE) {
            listAdapter?.loadMoreEnd(true)
        }
    }

    /**
     * 加载更多完成
     */
    protected fun finishLoadMore(datas: MutableList<D>) {
        listAdapter?.addData(datas)
        if (datas?.size < C.PAGE_SIZE) {
            listAdapter?.loadMoreEnd(true)
        } else {
            listAdapter?.loadMoreComplete()
        }
    }

    /**
     * 排版方式
     */
    protected fun layoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    inner class ListAdapter
    constructor(datas: MutableList<D>) : BaseAdapter<D, BaseViewHolder>(itemLayout(), datas) {
        override fun convert(helper: BaseViewHolder?, item: D) {
            myHolder(helper, item)
        }
    }
}