package com.kzj.mall.ui.activity

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
import com.kzj.mall.entity.DataHelper

class ConfirmOrderActivity : BaseActivity<IPresenter, ActivityConfirmOrderBinding>(), View.OnClickListener {
    val CHECK_ALIPAY = 0
    val CHECK_ARRIVE_PAY = 1

    var payCheck = CHECK_ALIPAY

    private var goodsAdapter: GoodsAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order
    }

    override fun setupComponent(appComponent: AppComponent?) {
    }

    override fun initData() {
        goodsAdapter = GoodsAdapter(DataHelper.orderGoods())
        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mBinding?.rvGoods?.adapter = goodsAdapter

        mBinding?.rlAlipay?.setOnClickListener(this)
        mBinding?.rlArrivePay?.setOnClickListener(this)
        mBinding?.rlAddress?.setOnClickListener(this)
    }

    inner class GoodsAdapter constructor(val goodsDatas: MutableList<String>)
        : BaseAdapter<String, BaseViewHolder>(R.layout.item_confirm_order_goods, goodsDatas) {
        override fun convert(helper: BaseViewHolder?, item: String?) {

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_alipay -> {
                if (payCheck != CHECK_ALIPAY){
                    mBinding?.ivAliCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivArriveCheck?.setImageResource(R.color.gray_default)
                    payCheck  =CHECK_ALIPAY
                }
            }
            R.id.rl_arrive_pay -> {
                if (payCheck != CHECK_ARRIVE_PAY){
                    mBinding?.ivArriveCheck?.setImageResource(R.mipmap.icon_cart_check)
                    mBinding?.ivAliCheck?.setImageResource(R.color.gray_default)
                    payCheck  =CHECK_ARRIVE_PAY
                }
            }
            R.id.rl_address->{
                val intent = Intent(this, AddressListActivity::class.java)
                startActivity(intent)
            }
        }
    }
}