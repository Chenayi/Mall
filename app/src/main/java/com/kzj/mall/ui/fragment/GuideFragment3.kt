package com.kzj.mall.ui.fragment

import android.view.animation.TranslateAnimation
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

    fun startAnim(){
        var animation1 = TranslateAnimation(200f, 0f,0f, 0f)
        animation1?.setDuration(100)
        mBinding?.ivTitle?.startAnimation(animation1)

        var animation2 = TranslateAnimation(200f, 0f,0f, 0f)
        animation2?.setDuration(200)
        mBinding?.ivSub?.startAnimation(animation2)


        var animation3 = TranslateAnimation(200f, 0f,0f, 0f)
        animation3?.setDuration(300)
        mBinding?.ivAnimView?.startAnimation(animation3)
    }
}