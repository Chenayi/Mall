package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGuide1Binding
import com.kzj.mall.di.component.AppComponent

class GuideFragment1:BaseFragment<IPresenter, FragmentGuide1Binding>() {

    companion object {
        fun newInstance():GuideFragment1{
            return GuideFragment1()
        }
    }

    override fun getLayoutId() = R.layout.fragment_guide1

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}