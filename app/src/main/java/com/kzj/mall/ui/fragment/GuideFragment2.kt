package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGuide2Binding
import com.kzj.mall.di.component.AppComponent

class GuideFragment2:BaseFragment<IPresenter, FragmentGuide2Binding>() {

    companion object {
        fun newInstance():GuideFragment2{
            return GuideFragment2()
        }
    }

    override fun getLayoutId() = R.layout.fragment_guide2

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}