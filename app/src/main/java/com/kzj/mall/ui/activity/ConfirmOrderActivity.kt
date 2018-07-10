package com.kzj.mall.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.base.BaseActivity
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.ActivityConfirmOrderBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.ui.dialog.ConfirmDialog
import com.kzj.mall.utils.LocalDatas
import com.kzj.mall.widget.RootLayout

class ConfirmOrderActivity : BaseActivity<IPresenter, ActivityConfirmOrderBinding>(), View.OnClickListener {
    val REQUEST_CODE_CREATE_ADDRESS = 101

    val CHECK_ALIPAY = 0
    val CHECK_ARRIVE_PAY = 1

    var payCheck = CHECK_ALIPAY
    var hasAddress = false

    private var goodsAdapter: GoodsAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        goodsAdapter = GoodsAdapter(LocalDatas.orderGoods())
        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding?.rvGoods?.adapter = goodsAdapter
        goodsAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(this@ConfirmOrderActivity, OrderGoodsListActivity::class.java)
            startActivity(intent)
        }

        mBinding?.rlAlipay?.setOnClickListener(this)
        mBinding?.rlArrivePay?.setOnClickListener(this)
        mBinding?.rlAddress?.setOnClickListener(this)
        mBinding?.llMultiGoods?.setOnClickListener(this)

        RootLayout.getInstance(this)
                ?.setOnLeftOnClickListener {
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
    }

    inner class GoodsAdapter constructor(val goodsDatas: MutableList<String>)
        : BaseAdapter<String, BaseViewHolder>(R.layout.item_confirm_order_goods, goodsDatas) {
        override fun convert(helper: BaseViewHolder?, item: String?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_ADDRESS) {
                mBinding?.llAddress?.visibility = View.VISIBLE
                mBinding?.tvCreateAddress?.visibility = View.GONE
                hasAddress = true
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_alipay -> {
                if (payCheck != CHECK_ALIPAY) {
                    mBinding?.ivAliCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivArriveCheck?.setImageResource(R.color.gray_default)
                    payCheck = CHECK_ALIPAY
                }
            }
            R.id.rl_arrive_pay -> {
                if (payCheck != CHECK_ARRIVE_PAY) {
                    mBinding?.ivArriveCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivAliCheck?.setImageResource(R.color.gray_default)
                    payCheck = CHECK_ARRIVE_PAY
                }
            }
            R.id.rl_address -> {
                if (hasAddress) {
                    val intent = Intent(this, AddressListActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, CreateAddressActivity::class.java)
                    startActivityForResult(intent, REQUEST_CODE_CREATE_ADDRESS)
                }
            }
            R.id.ll_multi_goods -> {
                val intent = Intent(this, OrderGoodsListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}