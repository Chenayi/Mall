package com.kzj.mall.ui.activity

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.R
import com.kzj.mall.adapter.OrderDetailAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityOrderDetailBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerOrderDetailComponent
import com.kzj.mall.di.module.OrderDetailModule
import com.kzj.mall.entity.order.IGoodsDetail
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.entity.order.OrderEntity
import com.kzj.mall.mvp.contract.OrderDetailContract
import com.kzj.mall.mvp.presenter.OrderDetailPresenter
import com.kzj.mall.utils.Utils
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context



class OrderDetailActivity : BaseActivity<OrderDetailPresenter, ActivityOrderDetailBinding>()
        , OrderDetailContract.View, View.OnClickListener {

    private var orderId: String? = null
    private var orderDetailAdapter: OrderDetailAdapter? = null
    private var headerView: View? = null
    private var footerView1: View? = null
    private var footerView2: View? = null

    private var tvOrderStatus: TextView? = null
    private var tvName: TextView? = null
    private var tvMobile: TextView? = null
    private var tvAddress: TextView? = null

    private var tvOrderCode: TextView? = null
    private var tvAddTime: TextView? = null
    private var tvCopyOrderCode: TextView? = null

    private var tvGoodsOldPrice: TextView? = null
    private var tvGoodsPrePrice: TextView? = null
    private var tvFee: TextView? = null
    private var tvAllGoodsPrice: TextView? = null
    private var tvPayPrice: TextView? = null

    override fun getLayoutId() = R.layout.activity_order_detail

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerOrderDetailComponent.builder()
                .appComponent(appComponent)
                .orderDetailModule(OrderDetailModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        orderId = intent?.getStringExtra("orderId")
        LogUtils.e("orderId ===> " + orderId)

        orderDetailAdapter = OrderDetailAdapter(ArrayList())
        headerView = layoutInflater.inflate(R.layout.header_order_detail, mBinding?.rvOrderDetail?.parent as ViewGroup, false)
        footerView1 = layoutInflater.inflate(R.layout.footer_order1, mBinding?.rvOrderDetail?.parent as ViewGroup, false)
        footerView2 = layoutInflater.inflate(R.layout.footer_order2, mBinding?.rvOrderDetail?.parent as ViewGroup, false)
        initViews()
        orderDetailAdapter?.addHeaderView(headerView)
        orderDetailAdapter?.addFooterView(footerView1)
        orderDetailAdapter?.addFooterView(footerView2)
        mBinding?.rvOrderDetail?.layoutManager = LinearLayoutManager(this)
        mBinding?.rvOrderDetail?.adapter = orderDetailAdapter


        mPresenter?.orderDetail(orderId)
    }

    private fun initViews() {
        tvOrderStatus = headerView?.findViewById(R.id.tv_order_status)
        tvName = headerView?.findViewById(R.id.tv_name)
        tvMobile = headerView?.findViewById(R.id.tv_mobile)
        tvAddress = headerView?.findViewById(R.id.tv_address)

        tvOrderCode = footerView1?.findViewById(R.id.tv_order_code)
        tvAddTime = footerView1?.findViewById(R.id.tv_add_time)
        tvCopyOrderCode = footerView1?.findViewById(R.id.tv_copy_order_code)
        tvCopyOrderCode?.setOnClickListener(this)

        tvGoodsOldPrice = footerView2?.findViewById(R.id.tv_goods_old_price)
        tvGoodsPrePrice = footerView2?.findViewById(R.id.tv_pre_price)
        tvFee = footerView2?.findViewById(R.id.tv_fee)
        tvAllGoodsPrice = footerView2?.findViewById(R.id.tv_all_goods_price)
        tvPayPrice = footerView2?.findViewById(R.id.tv_pay_price)
    }

    override fun orderDetail(orderDetailEntity: OrderDetailEntity?) {
        val order = orderDetailEntity?.order
        when (order?.orderStatus) {
            OrderEntity.ORDER_STATUS_WAIT_PAY -> {
                tvOrderStatus?.setText("订单待付款")
                mBinding?.llBottom?.visibility = View.VISIBLE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.tvHandle?.setText("立即支付")
            }
            OrderEntity.ORDER_STATUS_WAIT_SEND -> {
                tvOrderStatus?.setText("订单待发货")
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.llBottom?.visibility = View.GONE
            }
            OrderEntity.ORDER_STATUS_WAIT_TAKE -> {
                tvOrderStatus?.setText("订单待收货")
                mBinding?.llBottom?.visibility = View.VISIBLE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.tvHandle?.setText("确认收货")
            }
            OrderEntity.ORDER_STATUS_FINISH -> {
                tvOrderStatus?.setText("订单已完成")
                mBinding?.llBottom?.visibility = View.VISIBLE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_8a9099_stroke_corners_9999)
                mBinding?.tvHandle?.setText("再次购买")
            }
        }

        tvName?.setText(order?.shippingPerson)
        tvMobile?.setText(Utils.subMobile(order?.shippingMobile))
        tvAddress?.setText(order?.shippingProvince + "省" + order?.shippingCity + "市" + order?.shippingCounty + order?.shippingAddress)
        tvOrderCode?.setText(order?.orderNo)
        tvAddTime?.setText(TimeUtils.millis2String(order?.addTime!!))
        tvGoodsOldPrice?.setText("¥" + order?.oldPrice)
        tvGoodsPrePrice?.setText("¥" + order?.prePrice)
        tvAllGoodsPrice?.setText("¥" + order?.moneyPaid)
        tvFee?.setText("¥" + order?.shippingFee)
        tvPayPrice?.setText("¥" + order?.moneyPaid)


        val iGoodsDetails = ArrayList<IGoodsDetail>()
        val orderGoods = orderDetailEntity?.ordergoods

        //单品
        orderGoods?.dpMap?.let {
            iGoodsDetails?.addAll(it)
        }

        //疗程
        orderGoods?.lcMap?.let {
            iGoodsDetails?.addAll(it)
        }

        //套餐
        orderGoods?.tcMap?.let {
            iGoodsDetails?.addAll(it)
        }

        orderDetailAdapter?.setNewData(iGoodsDetails)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_copy_order_code -> {
                val orderCode = tvOrderCode?.text?.toString()?.trim()
                if (!TextUtils.isEmpty(orderCode)) {
                    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val mClipData = ClipData.newPlainText("复制订单编号", orderCode)
                    cm.setPrimaryClip(mClipData)
                    ToastUtils.showShort("复制成功")
                }
            }
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
}