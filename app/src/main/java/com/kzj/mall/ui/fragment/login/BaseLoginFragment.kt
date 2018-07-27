package com.kzj.mall.ui.fragment.login

import android.content.Intent
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentLoginBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerLoginComponent
import com.kzj.mall.di.module.LoginModule
import com.kzj.mall.mvp.contract.LoginContract
import com.kzj.mall.mvp.presenter.LoginPresenter
import com.kzj.mall.ui.activity.RegisterActivity

abstract class BaseLoginFragment : BaseFragment<LoginPresenter, FragmentLoginBinding>(), View.OnClickListener, LoginContract.View {
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


    override fun initData() {
        if (isCode()) {
            mBinding?.tvRequestCode?.visibility = View.VISIBLE
        } else {
            mBinding?.tvRequestCode?.visibility = View.GONE
        }

        mBinding?.tvRegister?.setOnClickListener(this)
    }

    abstract fun isCode(): Boolean

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register -> {
                val intent = Intent(context, RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}