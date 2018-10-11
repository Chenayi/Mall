package com.kzj.mall.ui.fragment.home

import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.HomeRecommendEntity

class OtherFragment : BaseHomeChildListFragment() {
    companion object {
        fun newInstance(): OtherFragment {
            val otherFragment = OtherFragment()
            return otherFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_other
    }

    override fun setBackGroundColor(colorRes: Int?) {
    }

    override fun useRoundedCorners() = false

    override fun initData() {
    }

    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
    }

    override fun onLoadMore() {
    }

    override fun showHomeDatas(homeEntity: HomeEntity?) {

    }

    override fun loadRecommendDatas(homeRecommendEntity: HomeRecommendEntity?) {
    }
}