package com.kzj.mall.ui.activity

import android.app.Activity
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityCreateAddressBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerCreateAddressComponent
import com.kzj.mall.di.module.CreateAddressModule
import com.kzj.mall.entity.address.CityEntity
import com.kzj.mall.entity.address.DistrictEntity
import com.kzj.mall.entity.address.ProvinceEntity
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.mvp.presenter.CreateAddressPresenter
import com.kzj.mall.ui.dialog.AddressDialog
import com.kzj.mall.widget.RootLayout

class CreateAddressActivity : BaseActivity<CreateAddressPresenter, ActivityCreateAddressBinding>(), TextWatcher
        , View.OnClickListener, CreateAddressContract.View {
    var rootLayout: RootLayout? = null

    private var checkP: ProvinceEntity.ProvinceBeen? = null
    private var checkC: CityEntity.CityBeen? = null
    private var checkD: DistrictEntity.DistrictBeen? = null

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
        rootLayout = RootLayout.getInstance(this)
        rootLayout?.setRightTextEnable(false)
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
                mPresenter?.addAddress(checkP?.provinceId!!, checkC?.cityId!!, checkD?.districtId!!, name!!, mobile!!, addressDetail!!, default)
            }
        })

        mBinding?.etPhone?.addTextChangedListener(this)
        mBinding?.etName?.addTextChangedListener(this)
        mBinding?.tvArea?.addTextChangedListener(this)
        mBinding?.etAddress?.addTextChangedListener(this)

        mBinding?.tvArea?.setOnClickListener(this)
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

    override fun addOrUpdateAddressSuccess() {
        setResult(Activity.RESULT_OK)
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
                            override fun onAddressCheck(p: ProvinceEntity.ProvinceBeen?, c: CityEntity.CityBeen?, d: DistrictEntity.DistrictBeen?) {
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