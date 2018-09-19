package com.kzj.mall.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.databinding.FragmentBaseListBinding
import com.kzj.mall.widget.ExpandLoadMoewView

abstract class BaseListFragment<P : IPresenter, D> : BaseFragment<P, FragmentBaseListBinding>() {
    protected var listAdapter: ListAdapter? = null
    protected var pageNo = 1
    private var loadMoreView: ExpandLoadMoewView? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_list
    }

    override fun initData() {
        listAdapter = ListAdapter(ArrayList())
        listAdapter?.setEmptyView(R.layout.empty_view, mBinding?.rv?.parent as ViewGroup)
        listAdapter?.emptyView?.findViewById<ImageView>(R.id.iv_empty)?.setImageResource(emptyViewIcon())
        listAdapter?.emptyView?.findViewById<TextView>(R.id.tv_empty_msg)?.setText(emptyMsg())
        listAdapter?.setOnItemClickListener { adapter, view, position ->
            onItemClick(view, position, listAdapter?.getItem(position))
        }
        mBinding?.rv?.layoutManager = layoutManager()
        mBinding?.rv?.adapter = listAdapter
        listAdapter?.setEnableLoadMore(isLoadMoreEnable())
        loadMoreView = ExpandLoadMoewView(endTips())
        listAdapter?.setLoadMoreView(loadMoreView)
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

    protected open fun endTips() = "好厉害，竟然让你到了底～"

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


    protected open fun emptyViewIcon() = R.mipmap.empty_default

    protected abstract fun emptyMsg(): String

    protected abstract fun onItemClick(view: View, position: Int, data: D?);

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
            listAdapter?.loadMoreEnd()
        }
    }

    /**
     * 加载更多完成
     */
    protected fun finishLoadMore(datas: MutableList<D>) {
        listAdapter?.addData(datas)
        if (datas?.size < C.PAGE_SIZE) {
            listAdapter?.loadMoreEnd()
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