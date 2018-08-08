package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityForgetPasswordBinding
import com.kzj.mall.di.component.AppComponent

class ForgetPasswordActivity : BaseActivity<IPresenter, ActivityForgetPasswordBinding>(), View.OnClickListener {
    override fun getLayoutId() = R.layout.activity_forget_password

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        mBinding?.rlContent?.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        mBinding?.ivClose?.setOnClickListener(this)
        mBinding?.tvNext?.setOnClickListener(this)
        mBinding?.tvCustomer?.setOnClickListener(this)

        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvNext?.isEnabled = canNext()
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
        if (resultCode == Activity.RESULT_OK && requestCode == 123){
            finish()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_customer -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ C.CUSTOMER_TEL))
                startActivity(dialIntent)
            }
            R.id.iv_close -> {
                finish()
            }
            R.id.tv_next -> {
                startActivityForResult(Intent(this, ForgetPasswordActivity2::class.java),123)
            }
        }
    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}