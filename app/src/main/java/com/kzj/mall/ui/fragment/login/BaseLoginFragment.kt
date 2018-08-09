package com.kzj.mall.ui.fragment.login

import android.content.Intent
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.databinding.FragmentLoginBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerLoginComponent
import com.kzj.mall.di.module.LoginModule
import com.kzj.mall.mvp.contract.LoginContract
import com.kzj.mall.mvp.presenter.LoginPresenter
import com.kzj.mall.ui.activity.RegisterActivity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kzj.mall.C


abstract class BaseLoginFragment : BaseFragment<LoginPresenter, FragmentLoginBinding>(), View.OnClickListener, LoginContract.View {
    private var isInit = false
    private var mobile: String? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_login
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(LoginModule(this))
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val onCreateView = super.onCreateView(inflater, container, savedInstanceState)
        if (isCode()) {
            mBinding?.tvRequestCode?.visibility = View.VISIBLE
            mBinding?.llPwd?.visibility = View.GONE
            mBinding?.etCode?.visibility = View.VISIBLE
            mBinding?.etPwd?.visibility = View.GONE
            mBinding?.etUsername?.visibility = View.GONE
            mBinding?.etMobile?.visibility = View.VISIBLE
        } else {
            mBinding?.tvRequestCode?.visibility = View.GONE
            mBinding?.llPwd?.visibility = View.VISIBLE
            mBinding?.etCode?.visibility = View.GONE
            mBinding?.etPwd?.visibility = View.VISIBLE
            mBinding?.etUsername?.visibility = View.VISIBLE
            mBinding?.etMobile?.visibility = View.GONE
        }
        return onCreateView
    }

    override fun initData() {
        mobile?.let {
            mBinding?.etUsername?.setText(it)
            mBinding?.etUsername?.setSelection(it.length)
        }
        isInit = true
        initLinstener()
    }

    fun setMobile(mobile: String?) {
        this.mobile = mobile
        if (isInit) {
            if (isCode()) {
                mBinding?.etMobile?.setText(mobile)
                mBinding?.etMobile?.setSelection(mobile?.length!!)
            } else {
                mBinding?.etUsername?.setText(mobile)
                mBinding?.etUsername?.setSelection(mobile?.length!!)
            }
        }
    }


    private fun initLinstener() {
        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvLogin?.isEnabled = canLogin()

                val mobile = mobile()
                if (!TextUtils.isEmpty(mobile)) {
                    mBinding?.ivClearMobile?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearMobile?.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        mBinding?.etUsername?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvLogin?.isEnabled = canLogin()

                val username = username()
                if (!TextUtils.isEmpty(username)) {
                    mBinding?.ivClearMobile?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearMobile?.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        mBinding?.etCode?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvLogin?.isEnabled = canLogin()

                val code = code()
                if (!TextUtils.isEmpty(code)) {
                    mBinding?.ivClearCode?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearCode?.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        mBinding?.etPwd?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mBinding?.tvLogin?.isEnabled = canLogin()

                val password = password()
                if (!TextUtils.isEmpty(password)) {
                    mBinding?.ivClearCode?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearCode?.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        mBinding?.etMobile?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val mobile = mobile()
                if (!TextUtils.isEmpty(mobile)) {
                    mBinding?.ivClearMobile?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearMobile?.visibility = View.GONE
                }
            } else {
                mBinding?.ivClearMobile?.visibility = View.GONE
            }
        }

        mBinding?.etUsername?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val username = username()
                if (!TextUtils.isEmpty(username)) {
                    mBinding?.ivClearMobile?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearMobile?.visibility = View.GONE
                }
            } else {
                mBinding?.ivClearMobile?.visibility = View.GONE
            }
        }

        mBinding?.etCode?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val code = code()
                if (!TextUtils.isEmpty(code)) {
                    mBinding?.ivClearCode?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearCode?.visibility = View.GONE
                }
            } else {
                mBinding?.ivClearCode?.visibility = View.GONE
            }
        }

        mBinding?.etPwd?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val password = password()
                if (!TextUtils.isEmpty(password)) {
                    mBinding?.ivClearCode?.visibility = View.VISIBLE
                } else {
                    mBinding?.ivClearCode?.visibility = View.GONE
                }
            } else {
                mBinding?.ivClearCode?.visibility = View.GONE
            }
        }

        mBinding?.tvRegister?.setOnClickListener(this)
        mBinding?.tvLogin?.setOnClickListener(this)
        mBinding?.ivWechat?.setOnClickListener(this)
        mBinding?.ivQq?.setOnClickListener(this)
        mBinding?.ivSina?.setOnClickListener(this)
        mBinding?.tvCustomer?.setOnClickListener(this)
        mBinding?.ivClearMobile?.setOnClickListener(this)
        mBinding?.ivClearCode?.setOnClickListener(this)
    }

    abstract fun isCode(): Boolean

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    private fun canLogin(): Boolean {
        val code = code()
        val mobile = mobile()
        val username = username()
        val password = password()

        if ((!TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(code))
                || (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password))) {
            return true
        }

        return false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register -> {
                val intent = Intent(context, RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            R.id.tv_customer -> {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + C.CUSTOMER_TEL))
                startActivity(dialIntent)
            }
            R.id.iv_clear_mobile -> {
                if (isCode()) {
                    mBinding?.etMobile?.setText("")
                } else {
                    mBinding?.etUsername?.setText("")
                }
            }
        }
    }


    protected fun username(): String? = mBinding?.etUsername?.text?.toString()?.trim()

    protected fun mobile(): String? {
        return mBinding?.etMobile?.text?.toString()?.trim()
    }

    protected fun code(): String? {
        return mBinding?.etCode?.text?.toString()?.trim()
    }

    protected fun password(): String? {
        return mBinding?.etPwd?.text?.toString()?.trim()
    }
}