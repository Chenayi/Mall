package com.kzj.mall.ui.activity

import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityDemandRegistrationBinding
import com.kzj.mall.di.component.AppComponent

class DemandRegistrationActivity:BaseActivity<IPresenter,ActivityDemandRegistrationBinding>() {
    override fun getLayoutId()= R.layout.activity_demand_registration

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}