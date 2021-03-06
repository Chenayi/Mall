package com.kzj.mall.ui.activity

import android.widget.ImageView
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityCooperationBinding
import com.kzj.mall.di.component.AppComponent

/**
 * 商业合作
 */
class CooperationActivity:BaseActivity<IPresenter,ActivityCooperationBinding>(){
    override fun getLayoutId()= R.layout.activity_cooperation

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.init()
    }

    override fun initData() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            finish()
        }
    }
}