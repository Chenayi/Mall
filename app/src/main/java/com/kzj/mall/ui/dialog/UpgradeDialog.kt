package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.kzj.mall.R
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.databinding.DialogUpgradeBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerUpgradeComponent
import com.kzj.mall.di.module.UpgradeModule
import com.kzj.mall.entity.VersionEntity
import com.kzj.mall.event.DownloadProgressEvent
import com.kzj.mall.mvp.contract.UpgradeContract
import com.kzj.mall.mvp.presenter.UpgradePresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

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
        EventBus.getDefault().register(this)

        val w = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(80f)
        val h = w * 375f / 760f
        mBinding?.ivTop?.layoutParams?.width = w
        mBinding?.ivTop?.layoutParams?.height = h?.toInt()
        mBinding?.ivTop?.requestLayout()

        versionEntity = arguments?.getSerializable("versionEntity") as VersionEntity


        val forceUpdate = versionEntity?.force_update

        //强制更新
        if (forceUpdate?.equals("1") == true) {
            mBinding?.llClose?.visibility = View.GONE
            mBinding?.btnOut?.visibility = View.VISIBLE
            mBinding?.btnOut?.setOnClickListener {
                activity?.finish()
            }
        }

        mBinding?.tvTitle?.text = "升级到${versionEntity?.version_code}新版本"
        mBinding?.tvContent?.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBinding?.tvContent?.text = versionEntity?.update_content
//        mBinding?.tvContent?.text = "1.全新设计\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付\n2.支持微信、支付宝支付"

        mBinding?.ivClose?.setOnClickListener {
            dismiss()
        }

        mBinding?.btnUpgrade?.setOnClickListener {
            mBinding?.llBtn?.visibility = View.GONE
            mBinding?.ivClose?.visibility = View.GONE
            mBinding?.numberProgressBar?.visibility = View.VISIBLE
            mPresenter?.downloadApk(versionEntity?.apk_address, versionEntity?.version_name)
        }
    }

    override fun intLayoutId() = R.layout.dialog_upgrade

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun progress(downloadProgressEvent: DownloadProgressEvent) {
        mBinding?.numberProgressBar?.setMax(downloadProgressEvent.total)
        mBinding?.numberProgressBar?.setProgress(downloadProgressEvent.progress)
    }

    override fun downLoadSuccess(apkFile: File) {
        AppUtils.installApp(apkFile)
        dismiss()
        activity?.finish()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun downLoadFail() {
        mBinding?.llBtn?.visibility = View.VISIBLE
        if (versionEntity?.force_update?.equals("1") == true) {
            mBinding?.llClose?.visibility = View.GONE
        } else {
            mBinding?.ivClose?.visibility = View.VISIBLE
        }
        mBinding?.numberProgressBar?.progress = 0
        mBinding?.numberProgressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}