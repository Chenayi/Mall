package com.kzj.mall.ui.activity

import android.widget.ImageView
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityAboutKzjBinding
import com.kzj.mall.di.component.AppComponent

/**'
 * 关于康之家
 */
class AboutKzjActivity : BaseActivity<IPresenter, ActivityAboutKzjBinding>() {
    override fun getLayoutId() = R.layout.activity_about_kzj

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
    }
}