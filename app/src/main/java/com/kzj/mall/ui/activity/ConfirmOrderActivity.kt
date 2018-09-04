package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.RequestCode
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityConfirmOrderBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.widget.RootLayout

class ConfirmOrderActivity : BaseActivity<IPresenter, ActivityConfirmOrderBinding>(), View.OnClickListener {

    val CHECK_ALIPAY = 0
    val CHECK_ARRIVE_PAY = 1

    var payCheck = CHECK_ALIPAY
    var hasAddress = false

    private var goodsAdapter: GoodsAdapter? = null

    private var buyEntity: BuyEntity? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        intent?.getSerializableExtra("buyEntity")?.let {
            buyEntity = it as BuyEntity
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

            val provinceName = it?.province?.provinceName
            val cityName = it?.city?.cityName
            val districtName = it?.district?.districtName
            val addressDetail = it?.addressDetail

            mBinding?.tvAddress?.text = provinceName + "省" + cityName + "市" + districtName + addressDetail

            hasAddress = true
        }

        mBinding?.rlAlipay?.setOnClickListener(this)
        mBinding?.rlArrivePay?.setOnClickListener(this)
        mBinding?.rlAddress?.setOnClickListener(this)
        mBinding?.llMultiGoods?.setOnClickListener(this)

        RootLayout.getInstance(this)
                ?.setOnLeftOnClickListener {
                    onBackPressedSupport()
                }
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
                hasAddress = true
            }
        }
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
                    startActivity(intent)
                } else {
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    startActivityForResult(intent, RequestCode.CREATE_ADDRESS)
                }
            }
            R.id.ll_multi_goods -> {
                val intent = Intent(this, OrderGoodsListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}