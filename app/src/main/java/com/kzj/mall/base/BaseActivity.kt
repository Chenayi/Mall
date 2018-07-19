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
import com.kzj.mall.event.CloseActivityEvent
import com.kzj.mall.ui.dialog.LoadingDialog
import com.yatoooon.screenadaptation.ScreenAdapterTools
import me.yokeyword.fragmentation.SupportActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

abstract class BaseActivity<P : IPresenter, D : ViewDataBinding> : SupportActivity() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var app: App? = null

    protected var mContext: Context? = null

    protected var mImmersionBar: ImmersionBar? = null

    protected var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        ScreenAdapterTools.getInstance().loadView(window.decorView);
        mContext = applicationContext
        app = application as App
        initImmersionBar()
        setupComponent(app?.getAppComponent())
        EventBus.getDefault().register(this)
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun setupComponent(appComponent: AppComponent?)
    abstract fun initData()

    protected open fun enableEventBus(): Boolean {
        return true
    }

    protected open fun enableEventBusCloseActivity(): Boolean {
        return true
    }

    @Subscribe
    fun closeActivity(closeActivityEvent: CloseActivityEvent) {
        if (enableEventBusCloseActivity()) {
            finish()
        }
    }

    protected open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.keyboardEnable(keyboardEnable())
                ?.keyboardMode(getKeyboardMode())
                ?.init()
    }

    protected open fun keyboardEnable(): Boolean {
        return false
    }


    protected open fun getKeyboardMode(): Int {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    }

    protected fun showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
                    .Builder(this)
                    .setCancelOutside(false)
                    .setBackCancelable(false)
                    .setShowMessage(false)
                    .create()
        }
        mLoadingDialog?.show()
    }

    protected fun dismissLoadingDialog() {
        mLoadingDialog?.dismiss()
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
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}