package com.kzj.mall.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.alipay.sdk.app.PayTask
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.base.BaseListFragment
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerOrderComponent
import com.kzj.mall.di.module.OrderModule
import com.kzj.mall.entity.PayResult
import com.kzj.mall.entity.order.OrderEntity
import com.kzj.mall.mvp.contract.OrderContract
import com.kzj.mall.mvp.presenter.OrderPresenter
import com.kzj.mall.ui.activity.OrderDetailActivity
import com.kzj.mall.ui.activity.PaySuccessActivity

class OrderFragment : BaseListFragment<OrderPresenter, OrderEntity.List>(), OrderContract.View {
    private var onderStatus = OrderEntity.ORDER_STATUS_ALL

    private var orderId: String? = null
    private var orderPrice: String? = null
    companion object {
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

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerOrderComponent.builder()
                .appComponent(appComponent)
                .orderModule(OrderModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        super.initData()

        listAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (view?.id == R.id.tv_handel){
                //订单状态
                val orderStatus = listAdapter?.data?.get(position)?.order_status
                when (orderStatus) {
                    OrderEntity.ORDER_STATUS_WAIT_PAY -> {
                        orderId = listAdapter?.data?.get(position)?.order_id
                        orderPrice = listAdapter?.data?.get(position)?.order_price

                        showLoadingDialog()
                        mPresenter.aliPayKey(orderId)
                    }

                    OrderEntity.ORDER_STATUS_WAIT_SEND -> {
                    }

                    OrderEntity.ORDER_STATUS_WAIT_TAKE -> {
                        val oid = listAdapter?.data?.get(position)?.order_id
                        mPresenter?.takeDelivery(oid)
                    }

                    OrderEntity.ORDER_STATUS_FINISH -> {
                    }

                    OrderEntity.ORDER_STATUS_CANCEL->{
                    }
                }
            }
        }

        if (onderStatus == OrderEntity.ORDER_STATUS_ALL) {
            mPresenter?.myOrderList(null, pageNo, false, true)
        } else {
            mPresenter?.myOrderList(onderStatus, pageNo, false, true)
        }
    }

    override fun endTips() = "您的订单这么少，这么快就到底了昵～"

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (isAcquired) {
            pageNo = 1
            if (onderStatus == OrderEntity.ORDER_STATUS_ALL) {
                mPresenter?.myOrderList(null, pageNo, false, true)
            } else {
                mPresenter?.myOrderList(onderStatus, pageNo, false, true)
            }
        }
    }

    private val SDK_PAY_FLAG = 1
    override fun showAliPayKey(key: String?) {
        dismissLoadingDialog()
        val payRunnable = Runnable {
            val alipay = PayTask(activity)
            val result = alipay.payV2(key, true)
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }

        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg?.what == SDK_PAY_FLAG) {
                val payResult = PayResult(msg.obj as Map<String, String>)
                val resultStatus = payResult.resultStatus
                val resultInfo = payResult.result
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    ToastUtils.showShort("支付成功")
                    val intent = Intent(context, PaySuccessActivity::class.java)
                    intent.putExtra("orderId", orderId)
                    intent.putExtra("payType", "支付宝")
                    intent.putExtra("orderPrice", orderPrice)
                    startActivity(intent)
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    ToastUtils.showShort("支付失败")
                }
            }
        }
    }

    override fun takeDeliverySuccess() {
        ToastUtils.showShort("收货成功")
        pageNo = 1
        if (onderStatus == OrderEntity.ORDER_STATUS_ALL) {
            mPresenter?.myOrderList(null, pageNo, false, false)
        } else {
            mPresenter?.myOrderList(onderStatus, pageNo, false, false)
        }
    }

    override fun showOrders(orders: MutableList<OrderEntity.List>?) {
        if (orders != null) {
            orders?.let {
                finishRefresh(it)
            }
        } else {
            finishRefresh(ArrayList())
        }
        mBinding?.rv?.scrollToPosition(0)
    }

    override fun loadMoreOrders(orders: MutableList<OrderEntity.List>?) {
        orders?.let {
            finishLoadMore(it)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun myHolder(helper: BaseViewHolder?, data: OrderEntity.List) {
        helper?.setText(R.id.tv_order_code, "订单编号  " + data?.order_code)
                ?.setText(R.id.tv_all_goods_num, "共" + data?.orderGoodses?.size + "件商品")
                ?.setText(R.id.tv_all_goods_price, "¥" + data?.order_price)
                ?.addOnClickListener(R.id.tv_handel)

        val size = data?.orderGoodses?.size!!

        //单件商品
        if (size <= 1) {
            val goods = data?.orderGoodses?.get(0)

            helper?.setGone(R.id.ll_single, true)
                    ?.setGone(R.id.ll_group, false)
                    ?.setText(R.id.tv_goods_name, goods?.goods_name)
                    ?.setText(R.id.tv_goods_price, "¥" + goods?.goods_info_price)
                    ?.setText(R.id.tv_goods_old_price, "¥" + goods?.goods_info_old_price)
                    ?.setText(R.id.tv_goods_num, "x" + goods?.goods_info_num)

            helper?.getView<TextView>(R.id.tv_goods_old_price)?.paint?.setFlags(Paint.STRIKE_THRU_TEXT_FLAG)

            GlideApp.with(context!!)
                    .load(goods?.goods_img)
                    .centerCrop()
                    .placeholder(R.color.gray_default)
                    .into(helper?.getView(R.id.iv_goods)!!)
        }

        //多件商品
        else {
            helper?.setGone(R.id.ll_single, false)
                    ?.setGone(R.id.ll_group, true)

            if (size == 2) {
                helper?.setVisible(R.id.iv_goods1, true)
                        ?.setVisible(R.id.iv_goods2, true)
                        ?.setVisible(R.id.iv_goods3, false)
                        ?.setVisible(R.id.iv_goods4, false)

                val goods1 = data?.orderGoodses?.get(0)
                val goods2 = data?.orderGoodses?.get(1)

                GlideApp.with(context!!)
                        .load(goods1?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods1)!!)

                GlideApp.with(context!!)
                        .load(goods2?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods2)!!)

            } else if (size == 3) {
                helper?.setVisible(R.id.iv_goods1, true)
                        ?.setVisible(R.id.iv_goods2, true)
                        ?.setVisible(R.id.iv_goods3, true)
                        ?.setVisible(R.id.iv_goods4, false)
                val goods1 = data?.orderGoodses?.get(0)
                val goods2 = data?.orderGoodses?.get(1)
                val goods3 = data?.orderGoodses?.get(2)

                GlideApp.with(context!!)
                        .load(goods1?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods1)!!)

                GlideApp.with(context!!)
                        .load(goods2?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods2)!!)

                GlideApp.with(context!!)
                        .load(goods3?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods3)!!)

            } else {
                helper?.setVisible(R.id.iv_goods1, true)
                        ?.setVisible(R.id.iv_goods2, true)
                        ?.setVisible(R.id.iv_goods3, true)
                        ?.setVisible(R.id.iv_goods4, true)

                val goods1 = data?.orderGoodses?.get(0)
                val goods2 = data?.orderGoodses?.get(1)
                val goods3 = data?.orderGoodses?.get(2)
                val goods4 = data?.orderGoodses?.get(3)

                GlideApp.with(context!!)
                        .load(goods1?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods1)!!)

                GlideApp.with(context!!)
                        .load(goods2?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods2)!!)

                GlideApp.with(context!!)
                        .load(goods3?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods3)!!)

                GlideApp.with(context!!)
                        .load(goods4?.goods_img)
                        .centerCrop()
                        .placeholder(R.color.gray_default)
                        .into(helper?.getView(R.id.iv_goods4)!!)
            }
        }

        //订单状态
        val orderStatus = data?.order_status
        when (orderStatus) {
            OrderEntity.ORDER_STATUS_WAIT_PAY -> {
                helper?.setText(R.id.tv_order_status, "待付款")
                        ?.setGone(R.id.tv_handel, true)
                        ?.setText(R.id.tv_handel, "立即支付")
                        ?.setTextColor(R.id.tv_handel, Color.WHITE)
                        ?.setBackgroundRes(R.id.tv_handel, R.drawable.background_orange_corners_9999)
            }

            OrderEntity.ORDER_STATUS_WAIT_SEND -> {
                helper?.setText(R.id.tv_order_status, "待发货")
                        ?.setGone(R.id.tv_handel, false)
            }

            OrderEntity.ORDER_STATUS_WAIT_TAKE -> {
                helper?.setText(R.id.tv_order_status, "待收货")
                        ?.setGone(R.id.tv_handel, true)
                        ?.setText(R.id.tv_handel, "确认收货")
                        ?.setTextColor(R.id.tv_handel, Color.WHITE)
                        ?.setBackgroundRes(R.id.tv_handel, R.drawable.background_orange_corners_9999)
            }

            OrderEntity.ORDER_STATUS_FINISH -> {
                helper?.setText(R.id.tv_order_status, "已完成")
                        ?.setGone(R.id.tv_handel, false)
                        ?.setTextColor(R.id.tv_handel, Color.parseColor("#8A9099"))
                        ?.setBackgroundRes(R.id.tv_handel, R.drawable.background_8a9099_stroke_corners_9999)
            }

            OrderEntity.ORDER_STATUS_CANCEL->{
                helper?.setText(R.id.tv_order_status, "已取消")
                        ?.setGone(R.id.tv_handel, false)
            }
        }
    }

    override fun emptyMsg() = "暂时没有相关订单哦～"

    override fun onItemClick(view: View, position: Int, data: OrderEntity.List?) {
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra("orderId", data?.order_id)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun itemLayout(): Int {
        return R.layout.item_order
    }

    override fun onRefresh() {
        pageNo = 1
        if (onderStatus == OrderEntity.ORDER_STATUS_ALL) {
            mPresenter?.myOrderList(null, pageNo, false, false)
        } else {
            mPresenter?.myOrderList(onderStatus, pageNo, false, false)
        }
    }

    override fun onLoadMore() {
        pageNo += 1
        if (onderStatus == OrderEntity.ORDER_STATUS_ALL) {
            mPresenter?.myOrderList(null, pageNo, true, false)
        } else {
            mPresenter?.myOrderList(onderStatus, pageNo, true, false)
        }
    }


}