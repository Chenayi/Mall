package com.kzj.mall.base

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.App
import com.kzj.mall.R
import com.kzj.mall.di.component.AppComponent
import com.yatoooon.screenadaptation.ScreenAdapterTools
import me.yokeyword.fragmentation.SupportActivity
import javax.inject.Inject

abstract class BaseActivity<P : IPresenter, D : ViewDataBinding> : SupportActivity() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var app: App? = null

    protected var mContext: Context? = null

    var mImmersionBar: ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        ScreenAdapterTools.getInstance().loadView(window.decorView);
        mContext = applicationContext
        app = application as App
        initImmersionBar()
        setupComponent(app?.getAppComponent())
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun setupComponent(appComponent: AppComponent?)
    abstract fun initData()

    protected open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.keyboardEnable(keyboardEnable())
                ?.keyboardMode(getKeyboardMode())
                ?.init()
    }

    protected fun keyboardEnable(): Boolean {
        return false
    }


    protected fun getKeyboardMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    fun jumpActivity(cls: Class<Any>) {
        var intent = Intent()
        intent.setClass(this, cls)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        mBinding?.unbind()
        mImmersionBar?.destroy()
        if (::mPresenter.isInitialized) {
            mPresenter.onDestory();
        }
        super.onDestroy()
    }
}