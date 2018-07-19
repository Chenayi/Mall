package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityRegisterBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerRegisterComponent
import com.kzj.mall.di.module.RegisterModule
import com.kzj.mall.mvp.contract.RegisterContract
import com.kzj.mall.mvp.presenter.RegisterPresenter

/**
 * 注册
 */
class RegisterActivity : BaseActivity<RegisterPresenter, ActivityRegisterBinding>(), RegisterContract.View, View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .registerModule(RegisterModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mBinding?.btnRequestCode?.setOnClickListener(this)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun sendCodeSuccess() {
        ToastUtils.showShort("验证码发送成功")
    }

    override fun sendCodeError(code: Int, errorMsg: String?) {
        ToastUtils.showShort("验证码发送失败")
        LogUtils.e(errorMsg)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_request_code -> {
                val mobile = mBinding?.etPhone?.text?.toString()
                mPresenter?.requestRegisterCode(mobile)
            }
        }
    }
}