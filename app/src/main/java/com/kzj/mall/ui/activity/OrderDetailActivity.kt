package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.OrderDetailAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityOrderDetailBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.utils.LocalDatas

class OrderDetailActivity : BaseActivity<IPresenter, ActivityOrderDetailBinding>() {
    private var orderDetailAdapter: OrderDetailAdapter? = null
    private var headerView: View? = null
    private var footerView1: View? = null
    private var footerView2: View? = null

    override fun getLayoutId() = R.layout.activity_order_detail

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        orderDetailAdapter = OrderDetailAdapter(LocalDatas.orderDetails())
        headerView = layoutInflater.inflate(R.layout.header_order_detail,mBinding?.rvOrderDetail?.parent as ViewGroup,false)
        footerView1 = layoutInflater.inflate(R.layout.footer_order1,mBinding?.rvOrderDetail?.parent as ViewGroup,false)
        footerView2 = layoutInflater.inflate(R.layout.footer_order2,mBinding?.rvOrderDetail?.parent as ViewGroup,false)
        orderDetailAdapter?.addHeaderView(headerView)
        orderDetailAdapter?.addFooterView(footerView1)
        orderDetailAdapter?.addFooterView(footerView2)
        mBinding?.rvOrderDetail?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvOrderDetail?.adapter = orderDetailAdapter
    }
}