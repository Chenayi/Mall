package com.kzj.mall.ui.activity.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityForgetPasswordBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerForgetPasswordComponent
import com.kzj.mall.di.module.ForgetPasswordModule
import com.kzj.mall.entity.LoginEntity
import com.kzj.mall.mvp.contract.ForgetPasswordContract
import com.kzj.mall.mvp.presenter.ForgetPasswordPresenter

class ForgetPasswordActivity : BaseActivity<ForgetPasswordPresenter, ActivityForgetPasswordBinding>(), View.OnClickListener, ForgetPasswordContract.View {
    private var countDown = false


    override fun getLayoutId() = R.layout.activity_forget_password

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerForgetPasswordComponent.builder()
                .appComponent(appComponent)
                .forgetPasswordModule(ForgetPasswordModule(this))
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
        mBinding?.ivClose?.setOnClickListener(this)
        mBinding?.tvNext?.setOnClickListener(this)
        mBinding?.tvCustomer?.setOnClickListener(this)
        mBinding?.ivClearCode?.setOnClickListener(this)
        mBinding?.ivClearMobile?.setOnClickListener(this)
        mBinding?.tvRequestCode?.setOnClickListener(this)

        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvNext?.isEnabled = canNext()
                mBinding?.ivClearMobile?.visibility = if (TextUtils.isEmpty(mobile())) View.GONE else View.VISIBLE

                if (!TextUtils.isEmpty(s) && countDown == false) {
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
                mBinding?.tvNext?.isEnabled = canNext()
                mBinding?.ivClearCode?.visibility = if (TextUtils.isEmpty(code())) View.GONE else View.VISIBLE
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
    }

    private fun canNext() = !TextUtils.isEmpty(mobile()) && !TextUtils.isEmpty(code())

    private fun mobile() = mBinding?.etMobile?.text?.toString()?.trim()

    private fun code() = mBinding?.etCode?.text?.toString()?.trim()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 123) {
            finish()
        }
    }


    override fun sendCodeSuccess() {
        countDown = true
        mBinding?.tvRequestCode?.isEnabled = false
        mBinding?.tvRequestCode?.setTextColor(Color.parseColor("#C2C6CC"))
        mPresenter?.disposable()
        mPresenter?.countDownTime(60)
    }

    override fun sendCodeError(code: Int, errorMsg: String?) {
    }

    override fun undateCountDownTime(time: Int?) {
        mBinding?.tvRequestCode?.setText(time?.toString() + "S后重新获取")
    }

    override fun countDownFinish() {
        val monbile = mobile()
        if (!TextUtils.isEmpty(monbile)) {
            mBinding?.tvRequestCode?.isEnabled = true
            mBinding?.tvRequestCode?.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        } else {
            mBinding?.tvRequestCode?.isEnabled = false
            mBinding?.tvRequestCode?.setTextColor(Color.parseColor("#C2C6CC"))
        }
        mBinding?.tvRequestCode?.setText("获取验证码")
        countDown = false
    }

    override fun upatePasswordSuccess(loginEntity: LoginEntity?) {
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_customer -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + C.CUSTOMER_TEL))
                startActivity(dialIntent)
            }
            R.id.iv_close -> {
                finish()
            }
            R.id.tv_next -> {
                val mobile = mobile()
                if (!RegexUtils.isMobileSimple(mobile)) {
                    ToastUtils.showShort("手机号码格式错误")
                    return
                }
                val code = code()
                if (code?.length!! < 6) {
                    ToastUtils.showShort("验证码错误")
                    return
                }
                val intent = Intent(this, ForgetPasswordActivity2::class.java)
                intent?.putExtra("mobile", mobile)
                intent?.putExtra("code", code)
                startActivityForResult(intent, 123)
            }
            R.id.iv_clear_mobile -> {
                mBinding?.etMobile?.setText("")
            }
            R.id.iv_clear_code -> {
                mBinding?.etCode?.setText("")
            }
            R.id.tv_request_code -> {
                val mobile = mobile()
                if (!RegexUtils.isMobileSimple(mobile)) {
                    ToastUtils.showShort("手机号码格式错误")
                    return
                }
                mPresenter?.requestCode(mobile)
            }
        }
    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}