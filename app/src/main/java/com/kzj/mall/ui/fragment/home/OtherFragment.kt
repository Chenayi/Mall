package com.kzj.mall.ui.fragment.home

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentHomeOtherBinding
import com.kzj.mall.di.component.AppComponent

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

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }

//    override fun isImmersionBarEnabled(): Boolean {
//        return true
//    }
//
//    override fun initImmersionBar() {
//        immersionBarColor = R.color.colorPrimary
//        super.initImmersionBar()
//    }

}