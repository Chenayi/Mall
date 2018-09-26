package com.kzj.mall.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kzj.mall.R
import android.view.WindowManager
import android.view.Gravity
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.App
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.dialog.LoadingDialog
import com.othershe.nicedialog.BaseNiceDialog
import com.othershe.nicedialog.ViewHolder
import com.yatoooon.screenadaptation.ScreenAdapterTools
import javax.inject.Inject


abstract class BaseDialog<P : IPresenter, D : ViewDataBinding> : BaseNiceDialog() {
    @Inject
    lateinit var mPresenter: P

    protected var mBinding: D? = null

    protected var mApp: App?=null

    protected var mLoadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(intLayoutId(), container, false)
        mBinding = DataBindingUtil.bind(view)
        ScreenAdapterTools.getInstance().loadView(view);
        mApp = activity?.getApplication() as App
        setUpComponent(mApp?.getAppComponent())
        initData()
        return view
    }

    override fun convertView(p0: ViewHolder?, p1: BaseNiceDialog?) {
    }
    abstract fun setUpComponent(appComponent: AppComponent?)

    abstract fun initData();


    protected fun showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog
                    .Builder(context!!)
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

    override fun onDestroy() {
        mBinding?.unbind()
        if (::mPresenter.isInitialized) {
            mPresenter.onDestory();
        }
        super.onDestroy()
    }
}