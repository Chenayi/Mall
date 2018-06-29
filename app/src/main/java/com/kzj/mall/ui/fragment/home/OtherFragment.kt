package com.kzj.mall.ui.fragment.home

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeOtherBinding

class OtherFragment :BaseFragment<IPresenter,FragmentHomeOtherBinding>() {

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

}