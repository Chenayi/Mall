package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityConfirmOrderBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerConfirmOrderComponent
import com.kzj.mall.di.module.ConfirmOrderModule
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.ConfirmOrderEntity
import com.kzj.mall.entity.address.Address
import com.kzj.mall.mvp.contract.ConfirmOrderContract
import com.kzj.mall.mvp.presenter.ConfirmOrderPresenter
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.Utils
import com.kzj.mall.widget.RootLayout

class ConfirmOrderActivity : BaseActivity<ConfirmOrderPresenter, ActivityConfirmOrderBinding>(), View.OnClickListener, ConfirmOrderContract.View {
    val CHECK_ALIPAY = 1
    val CHECK_ARRIVE_PAY = 0

    var payCheck = CHECK_ALIPAY
    var hasAddress = false

    private var goodsAdapter: GoodsAdapter? = null

    private var buyEntity: BuyEntity? = null

    private var addressId: String? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
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

//            val cartIds = buyEntity?.shoppingCartIds
//            for (i in 0 until cartIds?.size!!){
//                LogUtils.e("cartId ===> "+cartIds?.get(i))
//            }
        }

        val goodsImgs = ArrayList<String>()
        buyEntity?.shoplist?.let {
            for (i in 0 until it?.size) {
                if (goodsImgs?.size >= 4) {
                    return
                }

                val appgoods = it?.get(i)?.appgoods
                if (appgoods != null) {
                    goodsImgs?.add(appgoods?.goods_img!!)
                } else {
                    val ggList = it?.get(i)?.ggList
                    if (ggList != null) {
                        for (j in 0 until ggList?.size!!) {
                            if (goodsImgs?.size >= 4) {
                                return
                            }

                            goodsImgs?.add(ggList?.get(j)?.goods_img!!)
                        }
                    }
                }
            }
        }

        goodsAdapter = GoodsAdapter(goodsImgs)
        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding?.rvGoods?.adapter = goodsAdapter
        goodsAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this@ConfirmOrderActivity, OrderGoodsListActivity::class.java)
            startActivity(intent)
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

    override fun submitOrderCallBack(confirmOrderEntity: ConfirmOrderEntity?) {
        dismissLoadingDialog()
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
                }
            }
            R.id.rl_arrive_pay -> {
                if (payCheck != CHECK_ARRIVE_PAY) {
                    mBinding?.ivArriveCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivAliCheck?.setImageResource(R.mipmap.check_nor)
                    payCheck = CHECK_ARRIVE_PAY
                }
            }
            R.id.rl_address -> {
                if (hasAddress) {
                    val intent = Intent(this, MyAddressListActivity::class.java)
                    intent?.putExtra("addressId", addressId)
                    startActivityForResult(intent, RequestCode.CHOOSE_ADDRESS)
                } else {
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    startActivityForResult(intent, RequestCode.CREATE_ADDRESS)
                }
            }
            R.id.ll_multi_goods -> {
                val intent = Intent(this, OrderGoodsListActivity::class.java)
                startActivity(intent)
            }

            R.id.tv_submit_order -> {
                showLoadingDialog()
                mPresenter?.submitOrder(
                        buyEntity?.shoppingCartIds,
                        payCheck?.toString(),
                        "remark",
                        buyEntity?.address?.addressId,
                        buyEntity?.sumPrice,
                        FloatUtils.format(buyEntity?.shippingCharge!!)
                )
            }
        }
    }
}