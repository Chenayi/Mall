package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivitySplashBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerSplashComponent
import com.kzj.mall.di.module.SplashModule
import com.kzj.mall.mvp.contract.SplashContract
import com.kzj.mall.mvp.presenter.SplashPresenter

class SplashActivity : BaseActivity<SplashPresenter, ActivitySplashBinding>(), SplashContract.View {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
    }

    override fun initData() {
        mPresenter?.delayFinish(3)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun delayFinish() {
        jumpActivity(MainActivity().javaClass)
        finish()
    }

}