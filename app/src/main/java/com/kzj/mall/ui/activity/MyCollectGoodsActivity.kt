package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityMyCollectGoodsBinding
import com.kzj.mall.di.component.AppComponent

class MyCollectGoodsActivity:BaseActivity<IPresenter,ActivityMyCollectGoodsBinding>() {
    override fun getLayoutId() = R.layout.activity_my_collect_goods

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
    }
}