package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGuide3Binding
import com.kzj.mall.di.component.AppComponent

class GuideFragment3:BaseFragment<IPresenter, FragmentGuide3Binding>() {

    companion object {
        fun newInstance():GuideFragment3{
            return GuideFragment3()
        }
    }

    override fun getLayoutId() = R.layout.fragment_guide3

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}