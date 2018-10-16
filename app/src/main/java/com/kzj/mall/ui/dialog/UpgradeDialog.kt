package com.kzj.mall.ui.dialog

import android.os.Bundle
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.databinding.DialogUpgradeBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerUpgradeComponent
import com.kzj.mall.di.module.UpgradeModule
import com.kzj.mall.entity.VersionEntity
import com.kzj.mall.mvp.contract.UpgradeContract
import com.kzj.mall.mvp.presenter.UpgradePresenter

class UpgradeDialog : BaseDialog<UpgradePresenter, DialogUpgradeBinding>(), UpgradeContract.View {

    private var versionEntity: VersionEntity? = null

    companion object {
        fun newInstance(versionEntity: VersionEntity?): UpgradeDialog {
            val upgradeDialog = UpgradeDialog()
            val b = Bundle()
            b?.putSerializable("versionEntity", versionEntity)
            upgradeDialog?.arguments = b
            return upgradeDialog
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
        DaggerUpgradeComponent.builder()
                .appComponent(appComponent)
                .upgradeModule(UpgradeModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        versionEntity = arguments?.getSerializable("versionEntity") as VersionEntity
        mBinding?.tvContent?.text = versionEntity?.update_content
    }

    override fun intLayoutId() = R.layout.dialog_upgrade


    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(code: Int, msg: String?) {
    }
}