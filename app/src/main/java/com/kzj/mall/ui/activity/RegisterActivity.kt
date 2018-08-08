package com.kzj.mall.ui.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityRegisterBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerRegisterComponent
import com.kzj.mall.di.module.RegisterModule
import com.kzj.mall.event.RegisterSuccessEvent
import com.kzj.mall.mvp.contract.RegisterContract
import com.kzj.mall.mvp.presenter.RegisterPresenter
import org.greenrobot.eventbus.EventBus

/**
 * 注册
 */
class RegisterActivity : BaseActivity<RegisterPresenter, ActivityRegisterBinding>(), RegisterContract.View, View.OnClickListener {
    private var isShowPwd = false

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
        mImmersionBar
                ?.fitsSystemWindows(true, R.color.white)
                ?.statusBarDarkFont(true, 0.5f)
                ?.init()
    }

    override fun initData() {
        initListener()
    }

    private fun initListener() {
        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvRegister?.isEnabled = canRegister()
                mBinding?.ivClearMobile?.visibility = if (TextUtils.isEmpty(mobile())) View.GONE else View.VISIBLE

                if (!TextUtils.isEmpty(s)) {
                    mBinding?.tvRequestCode?.isEnabled = true
                    mBinding?.tvRequestCode?.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                } else {
                    mBinding?.tvRequestCode?.isEnabled = false
                    mBinding?.tvRequestCode?.setTextColor(Color.parseColor("#C2C6CC"))
                }
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

        mBinding?.ivEye?.setOnClickListener(this)
        mBinding?.tvRequestCode?.setOnClickListener(this)
        mBinding?.tvRegister?.setOnClickListener(this)
        mBinding?.ivClearMobile?.setOnClickListener(this)
        mBinding?.ivClearCode?.setOnClickListener(this)
        mBinding?.ivClearPwd?.setOnClickListener(this)
        mBinding?.ivClose?.setOnClickListener(this)
        mBinding?.tvRegister?.setOnClickListener(this)
        mBinding?.tvCustomer?.setOnClickListener(this)
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

    override fun registerSuccess(mobile: String?) {
        EventBus.getDefault().post(RegisterSuccessEvent(mobile!!))
        onBackPressedSupport()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_eye -> {
                if (!isShowPwd) {
                    mBinding?.ivEye?.setImageResource(R.mipmap.eye_open)
                    mBinding?.etPwd?.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mBinding?.ivEye?.setImageResource(R.mipmap.eye_close)
                    mBinding?.etPwd?.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mBinding?.etPwd?.requestFocus()
                mBinding?.etPwd?.setSelection(password()?.length!!)
                isShowPwd = !isShowPwd
            }
            R.id.iv_close -> {
                onBackPressedSupport()
            }
            R.id.tv_request_code -> {
                mPresenter?.requestRegisterCode(mobile())
            }
            R.id.tv_register -> {
                mPresenter?.register(mobile(), code(), password())
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
            R.id.tv_customer -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + C.CUSTOMER_TEL))
                startActivity(dialIntent)
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