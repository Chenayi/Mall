package com.kzj.mall.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.adapter.SearchGridAdapter
import com.kzj.mall.adapter.SearchListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivitySearchBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerSearchComponent
import com.kzj.mall.di.module.SearchModule
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.mvp.contract.SearchContract
import com.kzj.mall.mvp.presenter.SearchPresenter
import com.kzj.mall.ui.dialog.DetailMorePop
import com.kzj.mall.widget.GoodsSortView
import com.kzj.mall.widget.HotSearchView
import com.kzj.mall.widget.SearchBar

class SearchActivity : BaseActivity<SearchPresenter, ActivitySearchBinding>()
        , HotSearchView.OnTagClickListener
        , SearchBar.OnSearchListener
        , SearchBar.OnBackClickListener
        , SearchBar.OnModeChangeListener
        , SearchContract.View
        , GoodsSortView.OnSortChangeListener
        , BaseQuickAdapter.RequestLoadMoreListener
        , BaseQuickAdapter.OnItemClickListener
        , SwipeRefreshLayout.OnRefreshListener {
    private var searchListAdapter: SearchListAdapter? = null
    private var searchGridAdapter: SearchGridAdapter? = null
    private var manager: RecyclerView.LayoutManager? = null

    private var keywords: String? = null
    private var curPage = 1

    private var sort = GoodsSortView.S_DEFAULT
    private var order = "DESC"
    private var typeWhat: String? = null

    /**
     * 切换前rv的第一个item的位置
     */
    private var lastPostion: Int = 0

    override fun getLayoutId() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        keywords = intent?.getStringExtra("keywords")
        super.onCreate(savedInstanceState)
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mBinding?.hotSearchView?.setDatas(hotSearchDatas())
        mBinding?.hotSearchView?.setOnTagClickListener(this)
        mBinding?.searchBar?.setOnSearchListener(this)
        mBinding?.searchBar?.setOnBackClickListener(this)
        mBinding?.searchBar?.setOnModeChangeListener(this)
        mBinding?.goodsSortView?.setOnSortChangeListener(this)

        searchListAdapter = SearchListAdapter(ArrayList())
        searchGridAdapter = SearchGridAdapter(ArrayList())

        searchListAdapter?.setEmptyView(R.layout.empty_view, mBinding?.rvGoods?.parent as ViewGroup)
        searchListAdapter?.emptyView?.findViewById<ImageView>(R.id.iv_empty)?.setImageResource(R.mipmap.empty_search)
        searchListAdapter?.emptyView?.findViewById<TextView>(R.id.tv_empty_msg)?.setText("抱歉，没找到您想要的商品～")

        searchGridAdapter?.setEmptyView(R.layout.empty_view, mBinding?.rvGoods?.parent as ViewGroup)
        searchGridAdapter?.emptyView?.findViewById<ImageView>(R.id.iv_empty)?.setImageResource(R.mipmap.empty_search)
        searchGridAdapter?.emptyView?.findViewById<TextView>(R.id.tv_empty_msg)?.setText("抱歉，没找到您想要的商品～")

        searchListAdapter?.setEnableLoadMore(true)
        searchListAdapter?.setOnLoadMoreListener(this, mBinding?.rvGoods)
        searchGridAdapter?.setEnableLoadMore(true)
        searchGridAdapter?.setOnLoadMoreListener(this, mBinding?.rvGoods)
        searchListAdapter?.setOnItemClickListener(this)
        searchGridAdapter?.setOnItemClickListener(this)


        manager = LinearLayoutManager(this)
        mBinding?.rvGoods?.layoutManager = manager
        mBinding?.rvGoods?.adapter = searchListAdapter
        mBinding?.refreshLayout?.setOnRefreshListener(this)

        if (TextUtils.isEmpty(keywords)) {
            keywords = "感冒药"
        }else{
            mBinding?.hotSearchView?.visibility = View.GONE
            mBinding?.llGoodsContent?.visibility = View.VISIBLE
            mBinding?.searchBar?.startSearch(keywords)
            searchWithDefault(true)
        }
    }

    override fun getKeyboardMode(): Int {
        if (!TextUtils.isEmpty(keywords)){
            return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        }
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
    }

    /**
     * 触发加载更多
     */
    override fun onLoadMoreRequested() {
        curPage += 1
        startSearch(false)
    }

    /**
     * 下拉刷新
     */
    override fun onRefresh() {
        curPage = 1
        startSearch(false)
    }


    /**
     * item 点击
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
     * 开始搜索
     */
    private fun startSearch(loading: Boolean) {
        when (sort) {
            GoodsSortView.S_DEFAULT -> {
                searchWithDefault(loading)
            }

            GoodsSortView.S_SALES -> {
                searchWithSales(loading)
            }

            GoodsSortView.S_PRICE -> {
                searchWithPrice(loading)
            }

            GoodsSortView.S_TYPE -> {
                searchWithType(loading)
            }
        }
    }

    /**
     * 默认
     */
    private fun searchWithDefault(loading: Boolean) {
        mPresenter?.searchWithDefault(this.keywords!!, loading, curPage)
    }

    /**
     * 按销量
     */
    private fun searchWithSales(loading: Boolean) {
        mPresenter?.searchWithSales(this.keywords!!, loading, curPage)
    }

    /**
     * 按价格
     */
    private fun searchWithPrice(loading: Boolean) {
        mPresenter?.searchWithPrice(this.keywords!!, order!!, loading, curPage)
    }

    /**
     * 按类型
     */
    private fun searchWithType(loading: Boolean) {
        mPresenter?.searchWithType(this.keywords!!, typeWhat, loading, curPage)
    }

    /**
     * 热门搜索关键词
     */
    fun hotSearchDatas(): MutableList<String> {
        val hotSearchDatas = ArrayList<String>()
        hotSearchDatas?.add("金戈")
        hotSearchDatas?.add("益安宁丸")
        hotSearchDatas?.add("安宫牛黄丸")
        hotSearchDatas?.add("舒筋健腰丸")
        hotSearchDatas?.add("汇仁肾宝")
        hotSearchDatas?.add("气血和")
        hotSearchDatas?.add("健力多")
        return hotSearchDatas
    }

    /**
     * 热门关键词点击搜索
     */
    override fun onTagClick(position: Int, text: String?) {
        mBinding?.searchBar?.startSearch(text)
        mBinding?.hotSearchView?.visibility = View.GONE
        mBinding?.llGoodsContent?.visibility = View.VISIBLE
        searchWithLoading(text!!)
    }

    /**
     * 搜索
     */
    override fun onSearch(text: String?) {
        mBinding?.hotSearchView?.visibility = View.GONE
        mBinding?.llGoodsContent?.visibility = View.VISIBLE
        searchWithLoading(text!!)
    }

    /**
     * 搜索
     */
    private fun searchWithLoading(keywords: String) {
        this.keywords = keywords
        curPage = 1
        startSearch(true)
        mBinding?.goodsSortView?.setDefault()
        mBinding?.goodsSortView?.setPopDefault()
        sort = GoodsSortView.S_DEFAULT
    }

    /**
     * 模式切换
     */
    override fun onModeChange(mode: Int) {
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

    /**
     * 排序切换
     */
    override fun onSortChange(sort: Int?, order: String?, typeWhat: String?) {
        curPage = 1
        this.sort = sort!!
        when (this.sort) {
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
    }

    /**
     * 搜索结果
     */
    override fun searchSuccess(searchEntity: SearchEntity?) {
        mBinding?.refreshLayout?.isRefreshing = false
        searchListAdapter?.setNewData(searchEntity?.results?.data!!)
        searchGridAdapter?.setNewData(searchEntity?.results?.data!!)
        if (searchEntity?.results?.data?.size!! < C.PAGE_SIZE){
            searchListAdapter?.loadMoreEnd()
            searchGridAdapter?.loadMoreEnd()
        }
        mBinding?.rvGoods?.scrollToPosition(0)
    }

    /**
     * 加载更多
     */
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


    override fun onBackClick() {
        onBackPressedSupport()
    }

    override fun onBackPressedSupport() {
        val openSearch = mBinding?.searchBar?.isOpenSearch()
        val first = mBinding?.searchBar?.isFirst()
        if (openSearch == true && first == false) {
            mBinding?.searchBar?.closeSearch()
            return
        }
        super.onBackPressedSupport()
    }
}