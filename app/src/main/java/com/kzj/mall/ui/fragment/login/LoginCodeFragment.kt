package com.kzj.mall.ui.fragment.login

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.ui.activity.LoginActivity


/**
 * 验证码登录
 */
class LoginCodeFragment : BaseLoginFragment() {

    companion object {
        fun newInstance(): LoginCodeFragment {
            val loginCodeFragment = LoginCodeFragment()
            return loginCodeFragment
        }
    }

    override fun isCode(): Boolean {
        return true
    }

    override fun initData() {
        super.initData()

        mBinding?.etMobile?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {
                    mBinding?.tvRequestCode?.isEnabled = true
                    mBinding?.tvRequestCode?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
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

        mBinding?.tvRequestCode?.setOnClickListener(this)
    }

    override fun loginSuccess() {
        (activity as LoginActivity).loginSuccess()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val id = v?.id
        if (id == R.id.tv_login) {
            val code = code()
            val mobile = mobile()
            mPresenter?.loginByCode(mobile, code)
        }else if (id == R.id.iv_clear_code){
            mBinding?.etCode?.setText("")
        }
    }
}