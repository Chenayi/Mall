package com.kzj.mall.ui.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.kzj.mall.C
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivitySplashBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerSplashComponent
import com.kzj.mall.di.module.SplashModule
import com.kzj.mall.mvp.contract.SplashContract
import com.kzj.mall.mvp.presenter.SplashPresenter
import com.kzj.mall.ui.dialog.ConfirmDialog
import permissions.dispatcher.*

@RuntimePermissions
class SplashActivity : BaseActivity<SplashPresenter, ActivitySplashBinding>(), SplashContract.View {
    private val REQUEST_SETTING = 123

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(SplashModule(this))
                .build()
                .inject(this)
    }

    override fun initImmersionBar() {
    }

    override fun initData() {
        requestPermissionWithPermissionCheck()
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun requestPermission() {
        mPresenter?.delayFinish(3)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationale(request: PermissionRequest) {
        ConfirmDialog.newInstance("取消","确定","需要存储权限")
                .setOnConfirmClickListener(object :ConfirmDialog.OnConfirmClickListener{
                    override fun onLeftClick() {
                        request.cancel()
                    }

                    override fun onRightClick() {
                        request.proceed()
                    }
                })
                .setOutCancel(false)
                .show(supportFragmentManager)
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    internal fun showNeverAsk() {
        ConfirmDialog.newInstance("取消","确定","请到设置中打开存储权限")
                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener{
                    override fun onRightClick() {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.fromParts("package", packageName, null)
                        startActivityForResult(intent, REQUEST_SETTING)
                    }

                    override fun onLeftClick() {
                        finish()
                    }
                })
                .setOutCancel(false)
                .show(supportFragmentManager)
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDenied() {
        requestPermissionWithPermissionCheck()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTING) {
            requestPermissionWithPermissionCheck()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onError(code: Int, msg: String?) {
    }

    override fun delayFinish() {
        val token = SPUtils.getInstance()?.getString(C.SP_TOKEN)
        if (!TextUtils.isEmpty(token)){
            C.IS_LOGIN = true
            C.TOKEN = token!!
        }
        jumpActivity(MainActivity().javaClass)
        finish()
    }

}