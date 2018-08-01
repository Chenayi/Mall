package com.kzj.mall.ui.fragment.home

import com.chad.library.adapter.base.util.ProviderDelegate
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeOtherBinding
import com.kzj.mall.di.component.AppComponent

class OtherFragment :BaseHomeChildListFragment() {
    companion object {
        fun newInstance(): OtherFragment {
            val otherFragment = OtherFragment()
            return otherFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home_other
    }

    override fun initData() {
    }
    override fun registerItemProvider(providerDelegate: ProviderDelegate) {
    }

    override fun onLoadMore() {
    }
}