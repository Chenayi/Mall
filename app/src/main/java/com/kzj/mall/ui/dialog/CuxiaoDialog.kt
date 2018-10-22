package com.kzj.mall.ui.dialog

import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogCuxiaoBinding
import com.kzj.mall.di.component.AppComponent

/**
 * 促销
 */
class CuxiaoDialog :BaseDialog<IPresenter,DialogCuxiaoBinding>(){

    companion object {
        fun newInstance():CuxiaoDialog{
            return CuxiaoDialog()
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_cuxiao

    override fun initData() {
        mBinding?.ivClose?.setOnClickListener {
            dismiss()
        }
    }
}