package com.kzj.mall.ui.fragment.login

import android.content.Intent
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.FragmentLoginBinding
import com.kzj.mall.ui.activity.RegisterActivity

abstract class BaseLoginFragment : BaseFragment<IPresenter, FragmentLoginBinding>(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_login
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_register -> {
                val intent = Intent(context,RegisterActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
    }
}