package com.kzj.mall.adapter.provider.order

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.entity.order.TcMap
import com.kzj.mall.ui.activity.GoodsDetailActivity
import com.kzj.mall.utils.FloatUtils

/**
 * 套餐
 */
class TcProvider : BaseItemProvider<TcMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail1

    override fun viewType() = 3

    override fun convert(helper: BaseViewHolder?, data: TcMap?, position: Int) {
        helper?.setText(R.id.tv_combination_name, data?.tcMaps?.get(0)?.goodsMarketingName)
        val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
        rvGoods?.setFocusableInTouchMode(false);
        rvGoods?.requestFocus();
        val orderGoodsAdapter = OrderGoodsAdapter(data?.tcMaps!!)
        orderGoodsAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(mContext, GoodsDetailActivity::class.java)
            intent?.putExtra(C.GOODS_INFO_ID, orderGoodsAdapter?.getItem(position)?.goodsInfoId)
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext?.startActivity(intent)
        }
        rvGoods?.layoutManager = LinearLayoutManager(mContext)
        rvGoods?.adapter = orderGoodsAdapter
    }


    class OrderGoodsAdapter constructor(goods: MutableList<OrderDetailEntity.TCMap>)
        : BaseAdapter<OrderDetailEntity.TCMap, BaseViewHolder>(R.layout.item_order_detail_tc, goods) {
        override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.TCMap?) {
            val goodsInfoPrice = FloatUtils.format(item?.goodsInfoSumPrice?.toFloat()!! / item?.goodsInfoNum?.toFloat()!!)

            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
                    ?.setText(R.id.tv_goods_price, "¥" + goodsInfoPrice)
                    ?.setText(R.id.tv_goods_num, "x" + item?.goodsInfoNum)
                    ?.setText(R.id.tv_goods_name, item?.goodsInfoName)

            GlideApp.with(mContext)
                    .load(item?.goodsImg)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)
        }

    }
}