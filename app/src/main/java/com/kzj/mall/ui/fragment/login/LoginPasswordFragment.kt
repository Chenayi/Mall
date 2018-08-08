package com.kzj.mall.ui.fragment.login

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.ui.activity.ForgetPasswordActivity
import com.kzj.mall.ui.activity.LoginActivity


/**
 * 密码登录
 */
class LoginPasswordFragment : BaseLoginFragment() {

    private var isShowPwd = false

    companion object {
        fun newInstance(): LoginPasswordFragment {
            val loginPasswordFragment = LoginPasswordFragment()
            return loginPasswordFragment
        }
    }

    override fun isCode(): Boolean {
        return false
    }

    override fun initData() {
        super.initData()
        mBinding?.ivEye?.setOnClickListener(this)
        mBinding?.tvForgetPwd?.setOnClickListener(this)
    }

    override fun loginSuccess() {
        (activity as LoginActivity).loginSuccess()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val id = v?.id
        if (id == R.id.iv_eye) {
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
        } else if (id == R.id.tv_login) {
            val password = mBinding?.etPwd?.text?.toString()?.trim()
            val mobile = mBinding?.etMobile?.text?.toString()?.trim()
            mPresenter?.loginByPassword(mobile, password)
        } else if (id == R.id.iv_clear_code) {
            mBinding?.etPwd?.setText("")
        } else if (id == R.id.tv_forget_pwd) {
            val intent = Intent(context, ForgetPasswordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}