package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.alipay.sdk.app.PayTask
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.gyf.barlibrary.ImmersionBar
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.databinding.ActivityConfirmOrderBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerConfirmOrderComponent
import com.kzj.mall.di.module.ConfirmOrderModule
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.PayResult
import com.kzj.mall.entity.order.ConfirmOrderEntity
import com.kzj.mall.entity.address.Address
import com.kzj.mall.event.CartChangeEvent
import com.kzj.mall.mvp.contract.ConfirmOrderContract
import com.kzj.mall.mvp.presenter.ConfirmOrderPresenter
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.Utils
import com.kzj.mall.widget.RootLayout
import org.greenrobot.eventbus.EventBus

class ConfirmOrderActivity : BaseActivity<ConfirmOrderPresenter, ActivityConfirmOrderBinding>(), View.OnClickListener, ConfirmOrderContract.View {
    val CHECK_ALIPAY = 1
    val CHECK_ARRIVE_PAY = 0

    private val SDK_PAY_FLAG = 1
    var payCheck = CHECK_ALIPAY
    var hasAddress = false
    var isFromCart = false

    private var goodsAdapter: GoodsAdapter? = null

    private var buyEntity: BuyEntity? = null

    private var addressId: String? = null

    private var orderId: String? = null
    private var orderPrice: String? = null

    /**
     * 线上支付运费
     */
    private var onLinePayShipping = 0.00F

    /**
     * 到付运费
     */
    private var arrivePayShipping = 0.00F

    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
    }

    override fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.fitsSystemWindows(true, R.color.fb)
                ?.statusBarDarkFont(true, 0.5f)
                ?.keyboardEnable(keyboardEnable())
                ?.keyboardMode(getKeyboardMode())
                ?.init()
    }

    override fun setupComponent(appComponent: AppComponent?) {
        DaggerConfirmOrderComponent.builder()
                .appComponent(appComponent)
                .confirmOrderModule(ConfirmOrderModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        intent?.getSerializableExtra("buyEntity")?.let {
            buyEntity = it as BuyEntity
        }

        intent?.getBooleanExtra("isFromCart", false)?.let {
            isFromCart = it
        }

        buyEntity?.shippingCharge?.let {
            onLinePayShipping = it
            arrivePayShipping = it + 10.00f
        }

        val goodsImgs = ArrayList<String>()
        buyEntity?.shoplist?.let {
            for (i in 0 until it?.size) {
                if (goodsImgs?.size < 4) {
                    val appgoods = it?.get(i)?.appgoods
                    if (appgoods != null) {
                        goodsImgs?.add(appgoods?.goods_img!!)
                    } else {
                        val ggList = it?.get(i)?.ggList
                        if (ggList != null) {
                            for (j in 0 until ggList?.size!!) {
                                if (goodsImgs?.size < 4){
                                    goodsImgs?.add(ggList?.get(j)?.c_goods?.goods_img!!)
                                }
                            }
                        }
                    }
                }
            }
        }

        //单件商品
        if (goodsImgs?.size == 1) {
            mBinding?.llOneGoods?.visibility = View.VISIBLE
            mBinding?.llMultiGoods?.visibility = View.GONE
            buyEntity?.shoplist?.get(0)?.appgoods.let {
                mBinding?.tvGoodsName?.text = it?.goods_name
                GlideApp.with(this)
                        .load(it?.goods_img)
                        .placeholder(R.color.gray_default)
                        .centerCrop()
                        .into(mBinding?.ivGoods!!)
            }
            val fl = buyEntity?.shoplist?.get(0)?.goods_price?.toFloat()!! / buyEntity?.shoplist?.get(0)?.goods_num?.toFloat()!!
            mBinding?.tvGoodsPrice1?.text = "¥" + FloatUtils.format(fl)
            mBinding?.tvGoodsNum?.text = "x" + buyEntity?.shoplist?.get(0)?.goods_num
        }
        //多件商品
        else {
            mBinding?.llOneGoods?.visibility = View.GONE
            mBinding?.llMultiGoods?.visibility = View.VISIBLE
            goodsAdapter = GoodsAdapter(goodsImgs)
            mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            mBinding?.rvGoods?.adapter = goodsAdapter
            goodsAdapter?.setOnItemClickListener { adapter, view, position ->
                val intent = Intent(this@ConfirmOrderActivity, OrderGoodsListActivity::class.java)
                intent.putExtra("buyEntity", buyEntity)
                startActivity(intent)
            }
        }

        mBinding?.tvGoodsPrice?.text = "¥" + buyEntity?.sumOldPrice
        mBinding?.tvPrePrice?.text = "¥" + buyEntity?.sumPrePrice
        mBinding?.tvCharge?.text = "¥" + FloatUtils.format(buyEntity?.shippingCharge!!)

        val f1 = buyEntity?.sumPrice?.toFloat()!!
        val f2 = buyEntity?.shippingCharge!!
        val realPrice = f1 + f2
        mBinding?.tvRealGoodsPrice?.text = "实付款：¥" + FloatUtils.format(realPrice)


        //地址
        buyEntity?.address?.let {
            mBinding?.tvCreateAddress?.visibility = View.GONE
            mBinding?.llAddress?.visibility = View.VISIBLE
            setAddress(it)
        }

        mBinding?.rlAlipay?.setOnClickListener(this)
        mBinding?.rlArrivePay?.setOnClickListener(this)
        mBinding?.rlAddress?.setOnClickListener(this)
        mBinding?.llMultiGoods?.setOnClickListener(this)

        RootLayout.getInstance(this)
                ?.setOnLeftOnClickListener {
                    onBackPressedSupport()
                }

        mBinding?.tvSubmitOrder?.setOnClickListener(this)
    }

    private fun setAddress(address: Address) {
        addressId = address?.addressId
        val provinceName = address?.province?.provinceName
        val cityName = address?.city?.cityName
        val districtName = address?.district?.districtName
        val addressDetail = address?.addressDetail
        mBinding?.tvAddress?.text = provinceName + "省" + cityName + "市" + districtName + addressDetail
        mBinding?.tvName?.text = address?.addressName
        mBinding?.tvMobile?.text = Utils.subMobile(address?.addressMoblie)
        address?.isDefault?.let {
            mBinding?.tvDefault?.visibility = if (it?.equals("1")) View.VISIBLE else View.GONE
        }

        hasAddress = true
    }

    inner class GoodsAdapter constructor(val goodsDatas: MutableList<String>)
        : BaseAdapter<String, BaseViewHolder>(R.layout.item_confirm_order_goods, goodsDatas) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
            GlideApp.with(mContext)
                    .load(item)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCode.CREATE_ADDRESS) {
                mBinding?.llAddress?.visibility = View.VISIBLE
                mBinding?.tvCreateAddress?.visibility = View.GONE
                data?.getSerializableExtra("address")?.let {
                    setAddress(it as Address)
                }
            } else if (requestCode == RequestCode.CHOOSE_ADDRESS) {
                data?.getSerializableExtra("address")?.let {
                    setAddress(it as Address)
                }
            }
        }
    }

    override fun showAliPayKey(key: String?) {
        dismissLoadingDialog()
        val payRunnable = Runnable {
            val alipay = PayTask(this@ConfirmOrderActivity)
            val result = alipay.payV2(key, true)

            LogUtils.e("result ===> " + result)

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
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    ToastUtils.showShort("支付成功")
                    val intent = Intent(this@ConfirmOrderActivity, PaySuccessActivity::class.java)
                    intent.putExtra("orderId", orderId)
                    intent.putExtra("payType", "支付宝")
                    intent.putExtra("orderPrice", orderPrice)
                    startActivity(intent)
                    finish()
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    ToastUtils.showShort(payResult.memo)
                }
            }
        }
    }

    override fun submitOrderCallBack(confirmOrderEntity: ConfirmOrderEntity?) {
        EventBus.getDefault().post(CartChangeEvent())

        this.orderId = confirmOrderEntity?.orderId
        this.orderPrice = confirmOrderEntity?.sumPrice

        if (payCheck == CHECK_ALIPAY) {
            mPresenter?.aliPayKey(confirmOrderEntity?.orderId)
        } else if (payCheck == CHECK_ARRIVE_PAY) {
            val intent = Intent(this, PaySuccessActivity::class.java)
            intent.putExtra("orderId", this.orderId)
            intent.putExtra("payType", "货到付款")
            intent.putExtra("orderPrice", this.orderPrice)
            startActivity(intent)
            finish()
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        dismissLoadingDialog()
    }

    override fun onError(code: Int, msg: String?) {
        dismissLoadingDialog()
        ToastUtils.showShort(msg)
    }

    override fun onBackPressedSupport() {
        ConfirmDialog.newInstance("优惠不等人,真的要走么？")
                .setOnConfirmClickListener(object : ConfirmDialog.OnConfirmClickListener {
                    override fun onLeftClick() {
                        finish()
                    }

                    override fun onRightClick() {
                    }
                })
                .show(supportFragmentManager)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_alipay -> {
                if (payCheck != CHECK_ALIPAY) {
                    mBinding?.ivAliCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivArriveCheck?.setImageResource(R.mipmap.check_nor)
                    payCheck = CHECK_ALIPAY

                    mBinding?.tvGoodsPrice?.text = "¥" + buyEntity?.sumOldPrice
                    mBinding?.tvPrePrice?.text = "¥" + buyEntity?.sumPrePrice
                    mBinding?.tvCharge?.text = "¥" + onLinePayShipping

                    val f1 = buyEntity?.sumPrice?.toFloat()!!
                    val f2 = onLinePayShipping
                    val realPrice = f1 + f2
                    mBinding?.tvRealGoodsPrice?.text = "实付款：¥" + FloatUtils.format(realPrice)
                }
            }
            R.id.rl_arrive_pay -> {
                if (payCheck != CHECK_ARRIVE_PAY) {
                    mBinding?.ivArriveCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivAliCheck?.setImageResource(R.mipmap.check_nor)
                    payCheck = CHECK_ARRIVE_PAY

                    mBinding?.tvGoodsPrice?.text = "¥" + buyEntity?.sumOldPrice
                    mBinding?.tvPrePrice?.text = "¥" + buyEntity?.sumPrePrice
                    mBinding?.tvCharge?.text = "¥" + arrivePayShipping

                    val f1 = buyEntity?.sumPrice?.toFloat()!!
                    val f2 = arrivePayShipping
                    val realPrice = f1 + f2
                    mBinding?.tvRealGoodsPrice?.text = "实付款：¥" + FloatUtils.format(realPrice)
                }
            }
            R.id.rl_address -> {
                if (hasAddress) {
                    val intent = Intent(this, MyAddressListActivity::class.java)
                    intent?.putExtra("addressId", addressId)
                    startActivityForResult(intent, RequestCode.CHOOSE_ADDRESS)
                } else {
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    intent?.putExtra("isManager", false)
                    startActivityForResult(intent, RequestCode.CREATE_ADDRESS)
                }
            }
            R.id.ll_multi_goods -> {
                val intent = Intent(this, OrderGoodsListActivity::class.java)
                intent.putExtra("buyEntity", buyEntity)
                startActivity(intent)
            }

            R.id.tv_submit_order -> {
                showLoadingDialog()
                val remark = mBinding?.etMark?.text?.toString()?.trim()


                var shopSumPrice = 0.00f
                var shippingCharge = 0.00f

                if (payCheck == CHECK_ALIPAY) {
                    shippingCharge = onLinePayShipping
                    shopSumPrice = buyEntity?.sumPrice?.toFloat()!! + onLinePayShipping
                } else if (payCheck == CHECK_ARRIVE_PAY) {
                    shippingCharge = arrivePayShipping
                    shopSumPrice = buyEntity?.sumPrice?.toFloat()!! + arrivePayShipping
                }

                mPresenter?.submitOrder(
                        buyEntity?.shoppingCartIds,
                        payCheck?.toString(),
                        remark,
                        addressId,
                        FloatUtils.format(shopSumPrice),
                        shippingCharge?.toString()
                )
            }
        }
    }
}