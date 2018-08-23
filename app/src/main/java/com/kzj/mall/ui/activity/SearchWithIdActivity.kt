package com.kzj.mall.ui.activity

import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.SearchGridAdapter
import com.kzj.mall.adapter.SearchListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivitySearchWithIdBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerSearchWithIdComponent
import com.kzj.mall.di.module.SearchWithIdModule
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.mvp.contract.SearchWithIdContract
import com.kzj.mall.mvp.presenter.SearchWithIdPresenter
import com.kzj.mall.widget.GoodsSortView
import com.kzj.mall.widget.RootLayout
import com.kzj.mall.widget.SearchBar

class SearchWithIdActivity : BaseActivity<SearchWithIdPresenter, ActivitySearchWithIdBinding>()
        , SearchWithIdContract.View
        , GoodsSortView.OnSortChangeListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    private var searchListAdapter: SearchListAdapter? = null
    private var searchGridAdapter: SearchGridAdapter? = null
    private var manager: RecyclerView.LayoutManager? = null
    private var curPage = 1

    private var sort = GoodsSortView.S_DEFAULT
    private var order = "DESC"
    private var typeWhat: String? = null

    private var mCid: String? = null
    private var mBrandID: String? = null
    private var mTitle: String? = null

    private var rootLayout: RootLayout? = null
    private var mode = SearchBar.MODE_LIST

    /**
     * 切换前rv的第一个item的位置
     */
    private var lastPostion: Int = 0

    override fun getLayoutId() = R.layout.activity_search_with_id

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerSearchWithIdComponent.builder()
                .appComponent(appComponent)
                .searchWithIdModule(SearchWithIdModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mCid = intent?.getStringExtra("cid")
        mBrandID = intent?.getStringExtra("brandID")
        mTitle = intent?.getStringExtra("title")

        rootLayout = RootLayout.getInstance(this)
        rootLayout?.setTitle(mTitle)
                ?.setOnRightOnClickListener {
                    if (mode == SearchBar.MODE_LIST) {
                        mode = SearchBar.MODE_GRID
                    } else {
                        mode = SearchBar.MODE_LIST
                    }

                    onModeChange()
                }

        mBinding?.refreshLayout?.setOnRefreshListener(this)
        mBinding?.goodsSortView?.setOnSortChangeListener(this)
        searchListAdapter = SearchListAdapter(ArrayList())
        searchGridAdapter = SearchGridAdapter(ArrayList())
        searchListAdapter?.setEnableLoadMore(true)
        searchListAdapter?.setOnLoadMoreListener(this, mBinding?.rvGoods)
        searchGridAdapter?.setEnableLoadMore(true)
        searchGridAdapter?.setOnLoadMoreListener(this, mBinding?.rvGoods)
        searchListAdapter?.setOnItemClickListener(this)
        searchGridAdapter?.setOnItemClickListener(this)
        manager = LinearLayoutManager(this)
        mBinding?.rvGoods?.layoutManager = manager
        mBinding?.rvGoods?.adapter = searchListAdapter

        searchWithDefault(true)
    }

    /**
     * item点击
     */
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val intent = Intent(this, GoodsDetailActivity::class.java)
        if (adapter is SearchListAdapter) {
            intent?.putExtra(C.GOODS_INFO_ID, adapter?.getItem(position)?.goods_info_id)
        } else if (adapter is SearchGridAdapter) {
            intent?.putExtra(C.GOODS_INFO_ID, adapter?.getItem(position)?.goods_info_id)
        }
        startActivity(intent)
    }

    /**
     * 默认
     */
    private fun searchWithDefault(isloading: Boolean?) {
        if (!TextUtils.isEmpty(mCid)) {
            mPresenter?.searchWithCidDefault(mCid!!, isloading, curPage)
            return
        }

        if (!TextUtils.isEmpty(mBrandID)) {
            mPresenter?.searchWithBrandIDDefault(mBrandID!!, isloading, curPage)
            return
        }
    }

    /**
     * 销量
     */
    private fun searchWithSales(isloading: Boolean?) {
        if (!TextUtils.isEmpty(mCid)) {
            mPresenter?.searchWithCidSales(mCid!!, isloading, curPage)
            return
        }

        if (!TextUtils.isEmpty(mBrandID)) {
            mPresenter?.searchWithBrandIDSales(mBrandID!!, isloading, curPage)
            return
        }
    }

    /**
     * 价格
     */
    private fun searchWithPrice(isloading: Boolean?) {
        if (!TextUtils.isEmpty(mCid)) {
            mPresenter?.searchWithCidPrice(mCid!!, order!!, isloading, curPage)
            return
        }

        if (!TextUtils.isEmpty(mBrandID)) {
            mPresenter?.searchWithBrandIDPrice(mBrandID!!, order!!, isloading, curPage)
            return
        }
    }

    /**
     * 类型
     */
    private fun searchWithType(isloading: Boolean?) {
        if (!TextUtils.isEmpty(mCid)) {
            mPresenter?.searchWithCidType(mCid!!, typeWhat, isloading, curPage)
            return
        }

        if (!TextUtils.isEmpty(mBrandID)) {
            mPresenter?.searchWithBrandIDType(mBrandID!!, typeWhat, isloading, curPage)
            return
        }
    }

    /**
     * 下拉刷新
     */
    override fun onRefresh() {
        curPage = 1
        when (sort) {
            GoodsSortView.S_DEFAULT -> {
                searchWithDefault(false)
            }

            GoodsSortView.S_SALES -> {
                searchWithSales(false)
            }

            GoodsSortView.S_PRICE -> {
                searchWithPrice(false)
            }

            GoodsSortView.S_TYPE -> {
                searchWithType(false)
            }
        }
    }

    /**
     * 加载更多
     */
    override fun onLoadMoreRequested() {
        curPage += 1
        when (sort) {
            GoodsSortView.S_DEFAULT -> {
                searchWithDefault(false)
            }

            GoodsSortView.S_SALES -> {
                searchWithSales(false)
            }

            GoodsSortView.S_PRICE -> {
                searchWithPrice(false)
            }

            GoodsSortView.S_TYPE -> {
                searchWithType(false)
            }
        }
    }

    /**
     * 模式切换
     */
    private fun onModeChange() {
        if (mode == SearchBar.MODE_LIST) {
            mBinding?.llContainer?.setBackgroundColor(Color.WHITE)

            val gridLayoutManager = mBinding?.rvGoods?.layoutManager as GridLayoutManager
            lastPostion = gridLayoutManager?.findFirstVisibleItemPosition()

            manager = LinearLayoutManager(this)
            mBinding?.rvGoods?.layoutManager = manager
            mBinding?.rvGoods?.adapter = searchListAdapter

            mBinding?.rvGoods?.scrollToPosition(lastPostion)
        } else if (mode == SearchBar.MODE_GRID) {
            mBinding?.llContainer?.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.gray_default))

            val linearLayoutManager = mBinding?.rvGoods?.layoutManager as LinearLayoutManager
            lastPostion = linearLayoutManager?.findFirstVisibleItemPosition()

            manager = GridLayoutManager(this, 2)
            mBinding?.rvGoods?.layoutManager = manager
            mBinding?.rvGoods?.adapter = searchGridAdapter

            mBinding?.rvGoods?.scrollToPosition(lastPostion)
        }
    }

    override fun onSortChange(sort: Int?, order: String?, typeWhat: String?) {
        curPage = 1
        when (sort) {
            GoodsSortView.S_DEFAULT -> {
                searchWithDefault(true)
            }

            GoodsSortView.S_SALES -> {
                searchWithSales(true)
            }

            GoodsSortView.S_PRICE -> {
                this.order = order!!
                searchWithPrice(true)
            }

            GoodsSortView.S_TYPE -> {
                if (typeWhat != null) {
                    this.typeWhat = typeWhat!!
                } else {
                    this.typeWhat = null
                }

                searchWithType(true)
            }
        }
        this.sort = sort!!
    }

    /**
     * 搜索结果
     */
    override fun searchSuccess(searchEntity: SearchEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        searchListAdapter?.setNewData(searchEntity?.results?.data!!)
        searchGridAdapter?.setNewData(searchEntity?.results?.data!!)
        mBinding?.rvGoods?.scrollToPosition(0)
    }

    override fun loadMoreSeccess(searchEntity: SearchEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        searchEntity?.results?.data?.let {
            searchListAdapter?.addData(it)
            searchGridAdapter?.addData(it)
            if (it.size < C.PAGE_SIZE) {
                searchListAdapter?.loadMoreEnd()
                searchGridAdapter?.loadMoreEnd()
            } else {
                searchListAdapter?.loadMoreComplete()
                searchGridAdapter?.loadMoreComplete()
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }
}