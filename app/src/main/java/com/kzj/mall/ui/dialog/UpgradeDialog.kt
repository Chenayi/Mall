package com.kzj.mall.ui.dialog

import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.databinding.DialogUpgradeBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerUpgradeComponent
import com.kzj.mall.di.module.UpgradeModule
import com.kzj.mall.mvp.contract.UpgradeContract
import com.kzj.mall.mvp.presenter.UpgradePresenter

class UpgradeDialog : BaseDialog<UpgradePresenter, DialogUpgradeBinding>(), UpgradeContract.View {
    override fun setUpComponent(appComponent: AppComponent?) {
        DaggerUpgradeComponent.builder()
                .appComponent(appComponent)
                .upgradeModule(UpgradeModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
    }

    override fun intLayoutId() = R.layout.dialog_upgrade


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(code: Int, msg: String?) {
    }
}