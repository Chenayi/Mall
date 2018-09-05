package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityCreateAddressBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerCreateAddressComponent
import com.kzj.mall.di.module.CreateAddressModule
import com.kzj.mall.entity.address.Address
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.mvp.presenter.CreateAddressPresenter
import com.kzj.mall.ui.dialog.AddressDialog
import com.kzj.mall.widget.RootLayout

class CreateAddressActivity : BaseActivity<CreateAddressPresenter, ActivityCreateAddressBinding>(), TextWatcher
        , View.OnClickListener, CreateAddressContract.View {
    var rootLayout: RootLayout? = null

    private var checkP: Address.Province? = null
    private var checkC: Address.City? = null
    private var checkD: Address.District? = null

    private var isUpdateAddress = false

    private var address: Address? = null

    private var addressId: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_create_address
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerCreateAddressComponent.builder()
                .appComponent(appComponent)
                .createAddressModule(CreateAddressModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {

        intent?.getBooleanExtra("isUpdateAddress", false)?.let {
            isUpdateAddress = it
        }

        if (isUpdateAddress) {
            intent?.getSerializableExtra("address")?.let {
                address = it as Address
                addressId = address?.addressId
                setAddress(address)
            }
        }

        rootLayout = RootLayout.getInstance(this)
        rootLayout?.setRightTextEnable(isUpdateAddress)
        rootLayout?.setOnRightOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val mobile = mBinding?.etPhone?.text?.toString()?.trim()
                if (!RegexUtils.isMobileSimple(mobile)) {
                    ToastUtils.showShort("请输入正确的手机号码")
                    return
                }

                val name = mBinding?.etName?.text?.toString()?.trim()
                val addressDetail = mBinding?.etAddress?.text?.toString()?.trim()
                val default = if (mBinding?.switchDefault?.isChecked == true) "1" else "0"
                mPresenter?.addAddress(checkP?.provinceId!!, checkC?.cityId!!, checkD?.districtId!!,
                        name!!, mobile!!, addressDetail!!, default,addressId)
            }
        })

        mBinding?.etPhone?.addTextChangedListener(this)
        mBinding?.etName?.addTextChangedListener(this)
        mBinding?.tvArea?.addTextChangedListener(this)
        mBinding?.etAddress?.addTextChangedListener(this)

        mBinding?.tvArea?.setOnClickListener(this)
    }

    private fun setAddress(address: Address?) {
        checkP = address?.province
        checkC = address?.city
        checkD = address?.district

        mBinding?.etName?.clearFocus()
        mBinding?.etName?.setText(address?.addressName)
        mBinding?.etPhone?.setText(address?.addressMoblie)
        mBinding?.tvArea?.setText(address?.province?.provinceName + "省" + address?.city?.cityName + "市" + address?.district?.districtName)
        mBinding?.etAddress?.setText(address?.addressDetail)
        mBinding?.switchDefault?.isChecked = (address?.isDefault?.equals("1") == true)
    }

    private fun isCanSave(): Boolean {
        val phone = mBinding?.etPhone?.text
        val name = mBinding?.etName?.text
        val area = mBinding?.tvArea?.text
        val address = mBinding?.etAddress?.text

        if (TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(area) ||
                TextUtils.isEmpty(address)) {
            return false
        }

        return true
    }


    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun addOrUpdateAddressSuccess(address: Address?) {
        val intent = Intent()
        intent?.putExtra("isUpdateAddress",isUpdateAddress)
        intent.putExtra("address", address)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_area -> {
                KeyboardUtils.hideSoftInput(this)
                AddressDialog.newInstance()
                        .setOnAddressCheckListener(object : AddressDialog.OnAddressCheckListener {
                            override fun onAddressCheck(p: Address.Province?, c: Address.City?, d: Address.District?) {
                                checkP = p
                                checkC = c
                                checkD = d
                                mBinding?.tvArea?.text = checkP?.provinceName + "省" + checkC?.cityName + "市" + checkD?.districtName
                            }
                        })
                        .setShowBottom(true)
                        .show(supportFragmentManager)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
        rootLayout?.setRightTextEnable(isCanSave())
    }
}