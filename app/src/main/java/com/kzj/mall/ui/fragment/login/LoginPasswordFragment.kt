package com.kzj.mall.ui.fragment.login


/**
 * 密码登录
 */
class LoginPasswordFragment : BaseLoginFragment() {

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

    }
}