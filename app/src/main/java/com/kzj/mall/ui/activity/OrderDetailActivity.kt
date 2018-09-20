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
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.widget.ImageView
import android.widget.LinearLayout
import com.alipay.sdk.app.PayTask
import com.kzj.mall.entity.PayResult


class OrderDetailActivity : BaseActivity<OrderDetailPresenter, ActivityOrderDetailBinding>()
        , OrderDetailContract.View, View.OnClickListener {

    private var orderId: String? = null
    private var orderDetailAdapter: OrderDetailAdapter? = null
    private var headerView: View? = null
    private var footerView1: View? = null
    private var footerView2: View? = null

    private var llExpress: LinearLayout? = null
    private var tvExpressNo: TextView? = null
    private var tvCopyExpressNo: TextView? = null
    private var ivOrderStatus: ImageView? = null
    private var tvOrderStatus: TextView? = null
    private var tvName: TextView? = null
    private var tvMobile: TextView? = null
    private var tvAddress: TextView? = null

    private var tvOrderCode: TextView? = null
    private var tvAddTime: TextView? = null
    private var tvPayType: TextView? = null
    private var tvCopyOrderCode: TextView? = null

    private var tvGoodsOldPrice: TextView? = null
    private var tvGoodsPrePrice: TextView? = null
    private var tvFee: TextView? = null
    private var tvAllGoodsPrice: TextView? = null
    private var tvPayPrice: TextView? = null

    private var order: OrderDetailEntity.Order? = null

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


        mBinding?.rvOrderDetail?.setFocusableInTouchMode(false);
        mBinding?.rvOrderDetail?.requestFocus();
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

        mBinding?.tvHandle?.setOnClickListener(this)

        mPresenter?.orderDetail(orderId)
    }

    private fun initViews() {

        llExpress = headerView?.findViewById(R.id.ll_express)
        tvExpressNo = headerView?.findViewById(R.id.tv_express_no)
        tvCopyExpressNo = headerView?.findViewById(R.id.tv_copy_express_no)
        tvCopyExpressNo?.setOnClickListener(this)
        ivOrderStatus = headerView?.findViewById(R.id.iv_order_status)
        tvOrderStatus = headerView?.findViewById(R.id.tv_order_status)
        tvName = headerView?.findViewById(R.id.tv_name)
        tvMobile = headerView?.findViewById(R.id.tv_mobile)
        tvAddress = headerView?.findViewById(R.id.tv_address)

        tvOrderCode = footerView1?.findViewById(R.id.tv_order_code)
        tvAddTime = footerView1?.findViewById(R.id.tv_add_time)
        tvPayType = footerView1?.findViewById(R.id.tv_pay_type)
        tvCopyOrderCode = footerView1?.findViewById(R.id.tv_copy_order_code)
        tvCopyOrderCode?.setOnClickListener(this)

        tvGoodsOldPrice = footerView2?.findViewById(R.id.tv_goods_old_price)
        tvGoodsPrePrice = footerView2?.findViewById(R.id.tv_pre_price)
        tvFee = footerView2?.findViewById(R.id.tv_fee)
        tvAllGoodsPrice = footerView2?.findViewById(R.id.tv_all_goods_price)
        tvPayPrice = footerView2?.findViewById(R.id.tv_pay_price)
    }

    override fun orderDetail(orderDetailEntity: OrderDetailEntity?) {
        order = orderDetailEntity?.order

        order?.expressno?.let {
            if (it?.size > 0) {
                llExpress?.visibility = View.VISIBLE
                tvExpressNo?.text = it?.get(0)?.expressNo
            }
        }

        when (order?.orderStatus) {
            OrderEntity.ORDER_STATUS_WAIT_PAY -> {
                ivOrderStatus?.setImageResource(R.mipmap.icon_wait_pay)
                tvOrderStatus?.setText("订单待付款")
                mBinding?.llBottom?.visibility = View.VISIBLE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.tvHandle?.setText("立即支付")
            }
            OrderEntity.ORDER_STATUS_WAIT_SEND -> {
                ivOrderStatus?.setImageResource(R.mipmap.icon_wait_send)
                tvOrderStatus?.setText("订单待发货")
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.llBottom?.visibility = View.GONE
            }
            OrderEntity.ORDER_STATUS_WAIT_TAKE -> {
                ivOrderStatus?.setImageResource(R.mipmap.icon_wait_take)
                tvOrderStatus?.setText("订单待收货")
                mBinding?.llBottom?.visibility = View.VISIBLE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_orange_corners_9999)
                mBinding?.tvHandle?.setText("确认收货")
            }
            OrderEntity.ORDER_STATUS_FINISH -> {
                ivOrderStatus?.setImageResource(R.mipmap.icon_finish)
                tvOrderStatus?.setText("订单已完成")
                mBinding?.llBottom?.visibility = View.GONE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_8a9099_stroke_corners_9999)
            }

            OrderEntity.ORDER_STATUS_CANCEL -> {
                ivOrderStatus?.setImageResource(R.mipmap.icon_finish)
                tvOrderStatus?.setText("订单已取消")
                mBinding?.llBottom?.visibility = View.GONE
                mBinding?.tvHandle?.setBackgroundResource(R.drawable.background_8a9099_stroke_corners_9999)
            }
        }

        tvName?.setText(order?.shippingPerson)
        tvMobile?.setText(Utils.subMobile(order?.shippingMobile))
        tvAddress?.setText(order?.shippingProvince + "省" + order?.shippingCity + "市" + order?.shippingCounty + order?.shippingAddress)
        tvOrderCode?.setText(order?.orderNo)
        tvAddTime?.setText(TimeUtils.millis2String(order?.addTime!!))

        //货到付款
        if (order?.orderLinePay?.equals("0") == true){
            tvPayType?.text = "货到付款"
        }
        //在线支付
        else{
            tvPayType?.text = "在线支付"
        }

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
            R.id.tv_handle -> {
                when (order?.orderStatus) {
                    OrderEntity.ORDER_STATUS_WAIT_PAY -> {
                        showLoadingDialog()
                        mPresenter?.aliPayKey(orderId)
                    }
                    OrderEntity.ORDER_STATUS_WAIT_SEND -> {
                    }
                    OrderEntity.ORDER_STATUS_WAIT_TAKE -> {
                        mPresenter?.takeDelivery(orderId)
                    }
                    OrderEntity.ORDER_STATUS_FINISH -> {
                    }
                }
            }
            R.id.tv_copy_express_no -> {
                val no = tvExpressNo?.text?.toString()?.trim()
                if (!TextUtils.isEmpty(no)) {
                    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val mClipData = ClipData.newPlainText("复制物流编号", no)
                    cm.setPrimaryClip(mClipData)
                    ToastUtils.showShort("复制成功")
                }
            }
        }
    }

    private val SDK_PAY_FLAG = 1
    override fun showAliPayKey(key: String?) {
        dismissLoadingDialog()
        val payRunnable = Runnable {
            val alipay = PayTask(this)
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
                    val intent = Intent(this@OrderDetailActivity, PaySuccessActivity::class.java)
                    intent.putExtra("orderId", orderId)
                    intent.putExtra("payType", "支付宝")
                    intent.putExtra("orderPrice", order?.moneyPaid)
                    startActivity(intent)
                    finish()
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    ToastUtils.showShort("支付失败：" + resultInfo)
                }
            }
        }
    }

    override fun takeDeliverySuccess() {
        ToastUtils.showShort("收货成功")
        finish()
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