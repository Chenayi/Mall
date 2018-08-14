package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivitySearchBinding
import com.kzj.mall.di.component.AppComponent

class SearchActivity:BaseActivity<IPresenter,ActivitySearchBinding>() {
    override fun getLayoutId() = R.layout.activity_search

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}