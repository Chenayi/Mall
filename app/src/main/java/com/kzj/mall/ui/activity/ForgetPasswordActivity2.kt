package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityForgetPassword2Binding
import com.kzj.mall.di.component.AppComponent

class ForgetPasswordActivity2 : BaseActivity<IPresenter, ActivityForgetPassword2Binding>(), View.OnClickListener {
    private var isShowPwd = false

    override fun getLayoutId() = R.layout.activity_forget_password2

    override fun setupComponent(appComponent: AppComponent?) {
    }
    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        mBinding?.rlContent?.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        mBinding?.ivClose?.setOnClickListener(this)
        mBinding?.tvFinish?.setOnClickListener(this)
        mBinding?.ivEye?.setOnClickListener(this)
        mBinding?.ivClearPassword?.setOnClickListener(this)
        mBinding?.tvCustomer?.setOnClickListener(this)

        mBinding?.etPassword?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvFinish?.isEnabled = canFinish()
                mBinding?.ivClearPassword?.visibility = if (TextUtils.isEmpty(password())) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        mBinding?.etPassword?.setOnFocusChangeListener { v, hasFocus ->
            mBinding?.ivClearPassword?.visibility = if (!hasFocus || TextUtils.isEmpty(password())) View.GONE else View.VISIBLE
        }

    }

    private fun canFinish() = !TextUtils.isEmpty(password())

    private fun password() = mBinding?.etPassword?.text?.toString()?.trim()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {
                finish()
            }
            R.id.iv_eye->{
                if (!isShowPwd) {
                    mBinding?.ivEye?.setImageResource(R.mipmap.eye_open)
                    mBinding?.etPassword?.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mBinding?.ivEye?.setImageResource(R.mipmap.eye_close)
                    mBinding?.etPassword?.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mBinding?.etPassword?.requestFocus()
                mBinding?.etPassword?.setSelection(password()?.length!!)
                isShowPwd = !isShowPwd
            }
            R.id.iv_clear_password->{
                mBinding?.etPassword?.setText("")
            }
            R.id.tv_customer -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ C.CUSTOMER_TEL))
                startActivity(dialIntent)
            }
            R.id.tv_finish->{
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}