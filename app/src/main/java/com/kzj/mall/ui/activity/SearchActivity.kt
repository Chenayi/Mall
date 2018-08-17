package com.kzj.mall.ui.activity

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.SearchGridAdapter
import com.kzj.mall.adapter.SearchListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivitySearchBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerSearchComponent
import com.kzj.mall.di.module.SearchModule
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.mvp.contract.SearchContract
import com.kzj.mall.mvp.presenter.SearchPresenter
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.GoodsSortView
import com.kzj.mall.widget.HotSearchView
import com.kzj.mall.widget.SearchBar

class SearchActivity : BaseActivity<SearchPresenter, ActivitySearchBinding>()
        , HotSearchView.OnTagClickListener
        , SearchBar.OnSearchListener
        , SearchBar.OnBackClickListener
        , SearchBar.OnModeChangeListener
        , SearchContract.View
        , GoodsSortView.OnSortChangeListener {
    private var searchListAdapter: SearchListAdapter? = null
    private var searchGridAdapter: SearchGridAdapter? = null
    private var manager: RecyclerView.LayoutManager? = null
    private var searches: MutableList<SearchEntity.Data>? = null
    private var keywords = "感冒药"

    /**
     * 切换前rv的第一个item的位置
     */
    private var lastPostion: Int = 0

    override fun getLayoutId() = R.layout.activity_search

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

        searches = ArrayList()
        searchListAdapter = SearchListAdapter(searches!!)
        searchGridAdapter = SearchGridAdapter((searches!!))

        manager = LinearLayoutManager(this)
        mBinding?.rvGoods?.layoutManager = manager
        mBinding?.rvGoods?.adapter = searchListAdapter
    }

    override fun getKeyboardMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
    }

    fun hotSearchDatas(): MutableList<String> {
        val hotSearchDatas = ArrayList<String>()
        hotSearchDatas?.add("金戈")
        hotSearchDatas?.add("益安宁丸")
        hotSearchDatas?.add("安宫牛黄丸")
        hotSearchDatas?.add("舒筋健腰丸")
        hotSearchDatas?.add("安宫牛黄丸")
        hotSearchDatas?.add("益安宁丸")
        hotSearchDatas?.add("金戈")
        return hotSearchDatas
    }

    override fun onTagClick(position: Int, text: String?) {
        mBinding?.searchBar?.startSearch(text)
        mBinding?.hotSearchView?.visibility = View.GONE
        mBinding?.llGoodsContent?.visibility = View.VISIBLE
        startSearch(text!!)
    }

    override fun onSearch(text: String?) {
        mBinding?.hotSearchView?.visibility = View.GONE
        mBinding?.llGoodsContent?.visibility = View.VISIBLE
        startSearch(text!!)
    }

    private fun startSearch(keywords: String) {
        mPresenter?.searchWithDefault(keywords)
        mBinding?.goodsSortView?.setDefault()
        this.keywords = keywords
    }

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

    override fun onSortChange(sort: Int?, order: String?) {
        when (sort) {
            GoodsSortView.S_DEFAULT -> {
                mPresenter?.searchWithDefault(this.keywords)
            }

            GoodsSortView.S_SALES -> {
                mPresenter?.searchWithSales(this.keywords)
            }

            GoodsSortView.S_PRICE -> {
                mPresenter?.searchWithPrice(this.keywords,order!!)
            }
        }
    }


    override fun searchSuccess(searchEntity: SearchEntity?) {
        searches?.clear()
        searches?.addAll(searchEntity?.results?.data!!)
        searchListAdapter?.setNewData(searches)
        searchGridAdapter?.setNewData(searches)
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