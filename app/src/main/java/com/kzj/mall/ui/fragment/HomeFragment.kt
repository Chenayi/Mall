package com.kzj.mall.ui.fragment

import android.databinding.ViewDataBinding
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<IPresenter,FragmentHomeBinding>() {

    companion object {
        fun newInstance(): HomeFragment {
            val homeFragment = HomeFragment()
            return homeFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {
    }
}