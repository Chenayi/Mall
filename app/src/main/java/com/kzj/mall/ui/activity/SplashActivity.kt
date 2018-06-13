package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivitySplashBinding
import com.kzj.mall.di.component.AppComponent

class SplashActivity : BaseActivity<IPresenter, ActivitySplashBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setupComponent(appComponent: AppComponent?) {

    }

    override fun initData() {

    }

}