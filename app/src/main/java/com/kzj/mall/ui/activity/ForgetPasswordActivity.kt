package com.kzj.mall.ui.activity

import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.gyf.barlibrary.ImmersionBar
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {
                finish()
            }
        }
    }
}