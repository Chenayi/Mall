package com.kzj.mall.ui.fragment.login


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
    }
}