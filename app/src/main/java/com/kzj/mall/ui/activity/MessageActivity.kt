package com.kzj.mall.ui.activity

import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMessageBinding
import com.kzj.mall.di.component.AppComponent

class MessageActivity : BaseActivity<IPresenter, ActivityMessageBinding>() {
    override fun getLayoutId() = R.layout.activity_message

//    override fun initImmersionBar() {
//        mImmersionBar = ImmersionBar.with(this)
//        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
//                ?.init()
//    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}