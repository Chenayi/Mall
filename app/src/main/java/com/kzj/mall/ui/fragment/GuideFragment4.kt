package com.kzj.mall.ui.fragment

import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentGuide4Binding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.activity.MainActivity

class GuideFragment4:BaseFragment<IPresenter, FragmentGuide4Binding>() {
    companion object {
        fun newInstance():GuideFragment4{
            return GuideFragment4()
        }
    }

    override fun getLayoutId() = R.layout.fragment_guide4

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mBinding?.tvIn?.setOnClickListener {
            jumpActivity(MainActivity().javaClass)
            activity?.finish()
        }
    }
}