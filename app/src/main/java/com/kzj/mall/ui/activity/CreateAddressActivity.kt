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
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.mvp.presenter.CreateAddressPresenter
import com.kzj.mall.widget.RootLayout

class CreateAddressActivity : BaseActivity<CreateAddressPresenter, ActivityCreateAddressBinding>(), TextWatcher
        , View.OnClickListener, CreateAddressContract.View {
    var rootLayout: RootLayout? = null

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

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar
                ?.statusBarDarkFont(true, 0.5f)
                ?.keyboardEnable(keyboardEnable())
                ?.keyboardMode(getKeyboardMode())
                ?.init()
    }

    override fun initData() {
        rootLayout = RootLayout.getInstance(this)
        rootLayout?.setStatusBarViewHeight(BarUtils.getStatusBarHeight())
        rootLayout?.setRightTextEnable(false)
        rootLayout?.setOnRightOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val mobile = mBinding?.etPhone?.text?.toString()
                if (!RegexUtils.isMobileSimple(mobile)) {
                    ToastUtils.showShort("请输入正确的手机号码")
                    return
                }
                setResult(Activity.RESULT_OK)
                finish()
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

    override fun retundArea(options1Items: MutableList<String>, options2Items: MutableList<MutableList<String>>
                            , options3Items: MutableList<MutableList<MutableList<String>>>) {
        val pvOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            //返回的分别是三个级别的选中位置
            val tx = options1Items.get(options1) +
                    options2Items.get(options1).get(options2) +
                    options3Items.get(options1).get(options2).get(options3)

            mBinding?.tvArea?.setText(tx)
        })

                .setTitleText("")
                .setSubmitColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCancelColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setDecorView(rootLayout?.parent as ViewGroup)
                .build<String>()

        pvOptions.setPicker(options1Items, options2Items, options3Items)
        pvOptions.show()
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_area -> {
                KeyboardUtils.hideSoftInput(this)
                mPresenter?.requestArea()
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