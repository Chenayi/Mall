package com.kzj.mall.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.kzj.mall.ui.dialog.LoadingDialog
import com.yinglan.keyboard.HideUtil
import org.greenrobot.eventbus.EventBus


abstract class BaseFragment2<P : IPresenter, D : ViewDataBinding> : Fragment() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var mApp: App? = null

    protected var mLoadingDialog: LoadingDialog? = null


    /**
     * 是否已获取过数据
     */
    protected var isAcquired = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(getLayoutId(), container, false)
        mBinding = DataBindingUtil.bind(view)
        ScreenAdapterTools.getInstance().loadView(view);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HideUtil.init(activity)

        mApp = activity?.application as App
        setupComponent(mApp?.getAppComponent())
        if (enableEventBus()){
            EventBus.getDefault().register(this)
        }
        initData()
        isAcquired = true
    }

    abstract fun getLayoutId(): Int
    abstract fun setupComponent(appComponent: AppComponent?)
    abstract fun initData()
    protected open fun enableEventBus() = false


    protected fun showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog
                    .Builder(context!!)
                    .setCancelOutside(false)
                    .setBackCancelable(true)
                    .setShowMessage(false)
                    .create()
        }
        mLoadingDialog?.show()
    }

    protected fun dismissLoadingDialog() {
        mLoadingDialog?.dismiss()
    }

    override fun onDestroy() {
        mBinding?.unbind()
        if (::mPresenter.isInitialized) {
            mPresenter.onDestory();
        }
        if (enableEventBus()){
            EventBus.getDefault().unregister(this)
        }
        super.onDestroy()
    }
}