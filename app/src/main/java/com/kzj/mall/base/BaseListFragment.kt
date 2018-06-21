package com.kzj.mall.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
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

        mBinding?.refreshLayout?.isEnabled = refreshEnable()
    }

    protected abstract fun myHolder(helper: BaseViewHolder?, data: D)

    protected abstract fun itemLayout(): Int

    protected fun refreshEnable(): Boolean {
        return true
    }

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