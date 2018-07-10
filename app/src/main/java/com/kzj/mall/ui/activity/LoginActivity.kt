package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityLoginBinding
import com.kzj.mall.di.component.AppComponent

class LoginActivity : BaseActivity<IPresenter, ActivityLoginBinding>(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mBinding?.tvLogin?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login -> {
                C.ISLOGIN = true
                ToastUtils.showShort("登录成功")
                finish()
            }
        }
    }
}