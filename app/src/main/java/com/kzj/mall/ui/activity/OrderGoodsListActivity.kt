package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.kzj.mall.R
import com.kzj.mall.adapter.OrderGoodsListAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityOrderGoodsListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.utils.LocalDatas

class OrderGoodsListActivity : BaseActivity<IPresenter, ActivityOrderGoodsListBinding>() {
    private var orderGoodsListAdapter: OrderGoodsListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_order_goods_list
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this)
        orderGoodsListAdapter = OrderGoodsListAdapter(LocalDatas.goodsListDatas())
        mBinding?.rvGoods?.adapter = orderGoodsListAdapter
    }
}