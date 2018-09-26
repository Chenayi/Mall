package com.kzj.mall.ui.dialog

import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogServiceNoteBinding
import com.kzj.mall.di.component.AppComponent

/**
 * 服务说明
 */
class ServiceNoteDialog :BaseDialog<IPresenter,DialogServiceNoteBinding>(){

    companion object {
        fun newInstance():ServiceNoteDialog{
            return ServiceNoteDialog()
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_service_note

    override fun initData() {
        mBinding?.ivClose?.setOnClickListener {
            dismiss()
        }
    }
}