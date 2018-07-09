package com.kzj.mall.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityCreateAddressBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.widget.RootLayout

class CreateAddressActivity : BaseActivity<IPresenter, ActivityCreateAddressBinding>(), TextWatcher {
    var rootLayout: RootLayout? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_create_address
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        rootLayout = RootLayout.getInstance(this)
        rootLayout?.setRightTextEnable(false)
        rootLayout?.setOnRightOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(this@CreateAddressActivity, AddressListActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        mBinding?.etPhone?.addTextChangedListener(this)
        mBinding?.etName?.addTextChangedListener(this)
        mBinding?.etArea?.addTextChangedListener(this)
        mBinding?.etAddress?.addTextChangedListener(this)
    }

    private fun isCanSave(): Boolean {
        val phone = mBinding?.etPhone?.text
        val name = mBinding?.etName?.text
        val area = mBinding?.etArea?.text
        val address = mBinding?.etAddress?.text

        if (TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(area) ||
                TextUtils.isEmpty(address)) {
            return false
        }

        return true
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        rootLayout?.setRightTextEnable(isCanSave())
    }
}