package com.kzj.mall.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yatoooon.screenadaptation.ScreenAdapterTools
import me.yokeyword.fragmentation.SupportFragment
import javax.inject.Inject
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.App
import com.kzj.mall.R
import com.kzj.mall.di.component.AppComponent
import com.yinglan.keyboard.HideUtil


abstract class BaseFragment<P : IPresenter, D : ViewDataBinding> : SupportFragment() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var mImmersionBar: ImmersionBar? = null

    protected var immersionBarColor = R.color.fb

    protected var mApp: App? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(getLayoutId(), container, false)
        mBinding = DataBindingUtil.bind(view)
        ScreenAdapterTools.getInstance().loadView(view);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HideUtil.init(activity)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mApp = activity?.application as App
        setupComponent(mApp?.getAppComponent())
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun setupComponent(appComponent: AppComponent?)
    abstract fun initData()


    override fun onSupportVisible() {
        super.onSupportVisible()
        if (isImmersionBarEnabled()) {
            initImmersionBar()
        }
    }

    protected open fun isImmersionBarEnabled(): Boolean {
        return false
    }

    protected open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, immersionBarColor)
                ?.init()
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