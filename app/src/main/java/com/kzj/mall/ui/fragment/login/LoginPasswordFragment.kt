package com.kzj.mall.ui.fragment.login

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.kzj.mall.R


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
            val pwd = mBinding?.etPwd?.text?.toString()
            pwd?.let {
                mBinding?.etPwd?.setSelection(it.length)
            }
            mBinding?.etPwd?.requestFocus()
            isShowPwd = !isShowPwd
        }
    }
}