package com.kzj.mall.ui.dialog

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.kzj.mall.R
import com.kzj.mall.adapter.OrderGoodsListAdapter
import com.kzj.mall.base.BaseDialog
import com.kzj.mall.base.IPresenter
import com.kzj.mall.databinding.DialogOrderGoodsListBinding
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.CartZSEntity
import com.kzj.mall.entity.cart.ICart

/**
 * 促销
 */
class OrderGoodsListDialog : BaseDialog<IPresenter, DialogOrderGoodsListBinding>() {
    private var buyEntity: BuyEntity? = null
    private var orderGoodsListAdapter: OrderGoodsListAdapter? = null


    companion object {
        fun newInstance(buyEntity: BuyEntity?): OrderGoodsListDialog {
            val orderGoodsListDialog = OrderGoodsListDialog()
            val b = Bundle()
            b.putSerializable("buyEntity", buyEntity)
            orderGoodsListDialog.arguments = b
            return orderGoodsListDialog
        }
    }

    override fun setUpComponent(appComponent: AppComponent?) {
    }

    override fun intLayoutId() = R.layout.dialog_order_goods_list

    override fun initData() {

        buyEntity = arguments?.getSerializable("buyEntity") as BuyEntity

        mBinding?.ivClose?.setOnClickListener {
            dismiss()
        }

        val iCarts = ArrayList<ICart>()
        var goodsNum = 0

        //下单即送
        val msMap = buyEntity?.msMap
        if (msMap != null && msMap?.goods_info != null) {
            if (msMap?.goods_info?.goods_stock!! > 0) {
                val cartZSEntity = CartZSEntity()
                cartZSEntity.msMap = msMap
                iCarts.add(cartZSEntity)
                goodsNum += 1
            }
        }

        buyEntity?.shoplist?.let {
            for (i in 0 until it?.size) {
                val type = it?.get(i)?.shopping_cart_type
                //套装
                if (type.equals("2")) {
                    val cartGroupEntity = CartGroupEntity()
                    cartGroupEntity?.groups = it.get(i)?.ggList
                    cartGroupEntity?.goods_pre_price = it.get(i)?.goods_pre_price
                    cartGroupEntity?.combination_name = it.get(i)?.combination_name
                    cartGroupEntity?.goods_num = it.get(i)?.goods_num
                    cartGroupEntity?.goods_price = it.get(i)?.goods_price
                    cartGroupEntity?.goods_stock = it.get(i)?.goods_stock
                    cartGroupEntity?.shopping_cart_id = it.get(i)?.shopping_cart_id
                    iCarts.add(cartGroupEntity)
                    goodsNum += it.get(i)?.ggList?.size!!
                }
                //单品 疗程
                else if (type.equals("0") || type.equals("1")) {
                    val singleEntity = it.get(i)?.appgoods
                    singleEntity?.shopping_cart_type = it.get(i)?.shopping_cart_type
                    singleEntity?.goods_pre_price = it.get(i)?.goods_pre_price
                    singleEntity?.combination_name = it.get(i)?.combination_name
                    singleEntity?.goods_num = it.get(i)?.goods_num
                    singleEntity?.goods_price = it.get(i)?.goods_price
                    singleEntity?.goods_stock = it.get(i)?.goods_stock
                    singleEntity?.goods_info_id = it.get(i)?.goods_info_id
                    singleEntity?.shopping_cart_id = it.get(i)?.shopping_cart_id
                    singleEntity?.promotionMap = it?.get(i)?.promotionMap
                    singleEntity?.let {
                        iCarts.add(it)
                    }
                    goodsNum += 1
                }
            }
        }

        mBinding?.tvGoodsNum?.text = "（共${goodsNum}件）"
        mBinding?.rvGoods?.layoutManager = LinearLayoutManager(context)
        orderGoodsListAdapter = OrderGoodsListAdapter(iCarts)
        orderGoodsListAdapter?.addFooterView(layoutInflater.inflate(R.layout.header_footer_line_gray_10dp
                , mBinding?.rvGoods?.parent as ViewGroup, false))
        mBinding?.rvGoods?.adapter = orderGoodsListAdapter
    }
}