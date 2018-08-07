package com.kzj.mall.ui.activity

import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityPersonInfoBinding
import com.kzj.mall.di.component.AppComponent

class PersonInfoActivity:BaseActivity<IPresenter,ActivityPersonInfoBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_person_info
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.colorPrimary)
                ?.init()
    }

    override fun initData() {
    }
}