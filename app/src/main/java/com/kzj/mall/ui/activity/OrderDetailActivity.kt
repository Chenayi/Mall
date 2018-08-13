package com.kzj.mall.ui.activity

import com.kzj.mall.R
import com.kzj.mall.adapter.OrderDetailAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityOrderDetailBinding
import com.kzj.mall.di.component.AppComponent

class OrderDetailActivity : BaseActivity<IPresenter, ActivityOrderDetailBinding>() {
    private val orderDetailAdapter: OrderDetailAdapter? = null

    override fun getLayoutId() = R.layout.activity_order_detail

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {

    }
}