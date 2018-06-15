package com.kzj.mall.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kzj.mall.App
import com.kzj.mall.di.component.AppComponent
import com.yatoooon.screenadaptation.ScreenAdapterTools
import me.yokeyword.fragmentation.SupportActivity
import javax.inject.Inject

abstract class BaseActivity<P : IPresenter, D : ViewDataBinding> : SupportActivity() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var app: App? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        ScreenAdapterTools.getInstance().loadView(window.decorView);
        app = application as App
        setupComponent(app?.getAppComponent())
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun setupComponent(appComponent: AppComponent?)
    abstract fun initData()

    fun jumpActivity(cls: Class<Any>) {
        var intent = Intent()
        intent.setClass(this,cls)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        mBinding?.unbind()
        if (::mPresenter.isInitialized) {
            mPresenter.onDestory();
        }
        super.onDestroy()
    }
}