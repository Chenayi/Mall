package com.kzj.mall.ui.activity

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
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

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        mBinding?.rlContent?.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        initListener()
    }

    private fun initListener() {
        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvRegister?.isEnabled = canRegister()
                mBinding?.ivClearMobile?.visibility = if (TextUtils.isEmpty(mobile())) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


        mBinding?.etCode?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvRegister?.isEnabled = canRegister()
                mBinding?.ivClearCode?.visibility = if (TextUtils.isEmpty(code())) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })



        mBinding?.etPwd?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvRegister?.isEnabled = canRegister()
                mBinding?.ivClearPwd?.visibility = if (TextUtils.isEmpty(password())) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })


        mBinding?.etMobile?.setOnFocusChangeListener { v, hasFocus ->
            mBinding?.ivClearMobile?.visibility = if (!hasFocus || TextUtils.isEmpty(mobile())) View.GONE else View.VISIBLE
        }

        mBinding?.etCode?.setOnFocusChangeListener { v, hasFocus ->
            mBinding?.ivClearCode?.visibility = if (!hasFocus || TextUtils.isEmpty(code())) View.GONE else View.VISIBLE
        }

        mBinding?.etPwd?.setOnFocusChangeListener { v, hasFocus ->
            mBinding?.ivClearPwd?.visibility = if (!hasFocus || TextUtils.isEmpty(password())) View.GONE else View.VISIBLE
        }

        mBinding?.tvRequestCode?.setOnClickListener(this)
        mBinding?.tvRegister?.setOnClickListener(this)
        mBinding?.ivClearMobile?.setOnClickListener(this)
        mBinding?.ivClearCode?.setOnClickListener(this)
        mBinding?.ivClearPwd?.setOnClickListener(this)
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
            R.id.tv_request_code -> {
                mPresenter?.requestRegisterCode(mobile())
            }
            R.id.tv_register -> {

            }
            R.id.iv_clear_mobile -> {
                mBinding?.etMobile?.setText("")
            }
            R.id.iv_clear_code -> {
                mBinding?.etCode?.setText("")
            }
            R.id.iv_clear_pwd -> {
                mBinding?.etPwd?.setText("")
            }
        }
    }

    private fun canRegister() = !TextUtils.isEmpty(mobile()) && !TextUtils.isEmpty(code()) && !TextUtils.isEmpty(password())

    private fun mobile() = mBinding?.etMobile?.text?.toString()?.trim()

    private fun code() = mBinding?.etCode?.text?.toString()?.trim()

    private fun password() = mBinding?.etPwd?.text?.toString()?.trim()

    override fun onBackPressedSupport() {
        KeyboardUtils.hideSoftInput(this)
        super.onBackPressedSupport()
    }
}