package com.kzj.mall.ui.activity

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import com.kzj.mall.R
import com.kzj.mall.adapter.SearchGridAdapter
import com.kzj.mall.adapter.SearchListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivitySearchBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.SearchEntity
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.HotSearchView
import com.kzj.mall.widget.SearchBar

class SearchActivity : BaseActivity<IPresenter, ActivitySearchBinding>()
        , HotSearchView.OnTagClickListener
        , SearchBar.OnSearchListener
        , SearchBar.OnBackClickListener
        , SearchBar.OnModeChangeListener {

    private var searchListAdapter: SearchListAdapter? = null
    private var searchGridAdapter:SearchGridAdapter?=null
    private var manager: RecyclerView.LayoutManager? = null
    private var searches: MutableList<SearchEntity>? = null

    override fun getLayoutId() = R.layout.activity_search

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mBinding?.hotSearchView?.setDatas(hotSearchDatas())
        mBinding?.hotSearchView?.setOnTagClickListener(this)
        mBinding?.searchBar?.setOnSearchListener(this)
        mBinding?.searchBar?.setOnBackClickListener(this)
        mBinding?.searchBar?.setOnModeChangeListener(this)

        searches = ArrayList()
        searchListAdapter = SearchListAdapter(searches!!)
        searchGridAdapter = SearchGridAdapter((searches!!))

        manager = LinearLayoutManager(this)
        mBinding?.rvGoods?.layoutManager = manager
        mBinding?.rvGoods?.adapter = searchListAdapter
    }

    override fun getKeyboardMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
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

        searches?.clear()
        searches?.addAll(LocalDatas.searches())
        searchListAdapter?.setNewData(searches)
        searchGridAdapter?.setNewData(searches)
    }

    override fun onSearch(text: String?) {
        mBinding?.hotSearchView?.visibility = View.GONE
        mBinding?.llGoodsContent?.visibility = View.VISIBLE

        searches?.clear()
        searches?.addAll(LocalDatas.searches())
        searchListAdapter?.setNewData(searches)
        searchGridAdapter?.setNewData(searches)
    }

    override fun onModeChange(mode: Int) {
        if (mode == SearchBar.MODE_LIST) {
            manager = LinearLayoutManager(this)
            mBinding?.rvGoods?.layoutManager = manager
            mBinding?.rvGoods?.adapter = searchListAdapter
        } else if (mode == SearchBar.MODE_GRID) {
            manager = GridLayoutManager(this,2)
            mBinding?.rvGoods?.layoutManager = manager
            mBinding?.rvGoods?.adapter = searchGridAdapter
        }
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