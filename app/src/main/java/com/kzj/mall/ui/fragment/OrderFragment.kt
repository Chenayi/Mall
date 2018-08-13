package com.kzj.mall.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.base.BaseListFragment
import com.kzj.mall.base.IPresenter
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.OrderEntity
import com.kzj.mall.ui.activity.OrderDetailActivity
import com.kzj.mall.utils.LocalDatas

class OrderFragment : BaseListFragment<IPresenter, OrderEntity>() {
    private var onderStatus = ORDER_STATUS_ALL


    companion object {
        val ORDER_STATUS_ALL = 0
        val ORDER_STATUS_WAIT_PAY = 1
        val ORDER_STATUS_WAIT_SEND = 2
        val ORDER_STATUS_WAIT_TAKE = 3
        val ORDER_STATUS_FINISH = 4

        fun newInstance(orderStatus: Int): OrderFragment {
            val orderFragment = OrderFragment()
            val arguments = Bundle()
            arguments.putInt("orderStatus", orderStatus)
            orderFragment.arguments = arguments
            return orderFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onderStatus = arguments?.getInt("orderStatus")!!
    }

    override fun myHolder(helper: BaseViewHolder?, data: OrderEntity) {
    }

    override fun onItemClick(view: View, position: Int, data: OrderEntity?) {
        val intent = Intent(context,OrderDetailActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun itemLayout(): Int {
        return R.layout.item_order
    }

    override fun initData() {
        super.initData()
        finishRefresh(LocalDatas.orderDatas())
    }

    override fun onRefresh() {
        finishRefresh(LocalDatas.orderDatas())
    }

    override fun onLoadMore() {
        val orderDatas = LocalDatas.orderDatas()
        orderDatas.removeAt(0)
        finishLoadMore(orderDatas)
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }
}