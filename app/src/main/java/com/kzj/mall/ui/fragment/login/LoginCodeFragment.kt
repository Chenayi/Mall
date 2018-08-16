package com.kzj.mall.ui.fragment.login

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.entity.LoginEntity
import com.kzj.mall.ui.activity.login.LoginActivity


/**
 * 验证码登录
 */
class LoginCodeFragment : BaseLoginFragment() {
    private var countDown = false

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
                if (!TextUtils.isEmpty(s) && countDown==false) {
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

    override fun sendCodeSuccess() {
        countDown = true
        mPresenter?.disposable()
        mPresenter?.countDownTime(60)
        mBinding?.tvRequestCode?.isEnabled = false
        mBinding?.tvRequestCode?.setTextColor(Color.parseColor("#C2C6CC"))
    }

    override fun sendCodeError(code: Int, errorMsg: String?) {
        ToastUtils.showShort(errorMsg)
    }

    override fun undateCountDownTime(time: Int?) {
        mBinding?.tvRequestCode?.setText(time?.toString() + "S后重新获取")
    }

    override fun countDownFinish() {
        val s = mobile()
        if (!TextUtils.isEmpty(s)) {
            mBinding?.tvRequestCode?.isEnabled = true
            mBinding?.tvRequestCode?.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        } else {
            mBinding?.tvRequestCode?.isEnabled = false
            mBinding?.tvRequestCode?.setTextColor(Color.parseColor("#C2C6CC"))
        }

        mBinding?.tvRequestCode?.setText("获取验证码")
        countDown = false
    }

    override fun loginSuccess(loginEntity: LoginEntity?) {
        C.IS_LOGIN = true
        C.TOKEN = loginEntity?.token!!
        SPUtils.getInstance().put(C.SP_TOKEN, loginEntity?.token)
        (activity as LoginActivity).loginSuccess()
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        val id = v?.id
        if (id == R.id.tv_login) {
            val code = code()
            val mobile = mobile()
            mPresenter?.loginByCode(mobile, code)
        } else if (id == R.id.iv_clear_code) {
            mBinding?.etCode?.setText("")
        } else if (id == R.id.tv_request_code) {
            val s = mobile()
            val isMobile = RegexUtils.isMobileSimple(s)
            if (!isMobile) {
                ToastUtils.showShort("手机号码格式错误")
                return
            }
            mPresenter?.disposable()
            mPresenter?.requestCode(s)
        }
    }
}