package com.kzj.mall.ui.dialog

import android.os.Bundle
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogAddressBinding
import com.kzj.mall.di.component.AppComponent

class AddressDialog : BaseDialog<IPresenter, DialogAddressBinding>() {

    companion object {
        fun newInstance(): AddressDialog {
            val addressDialog = AddressDialog()
            val b = Bundle()
            addressDialog?.arguments = b
            return addressDialog
        }
    }

    override fun initData() {
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_address
}