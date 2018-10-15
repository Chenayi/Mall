package com.kzj.mall.adapter.provider.order

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.order.OrderDetailEntity
import com.kzj.mall.entity.order.TcMap

/**
 * 套餐
 */
class TcProvider : BaseItemProvider<TcMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail1

    override fun viewType() = 3

    override fun convert(helper: BaseViewHolder?, data: TcMap?, position: Int) {
        helper?.setText(R.id.tv_combination_name, data?.tcMaps?.get(0)?.goodsMarketingName)
        val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
        val orderGoodsAdapter = OrderGoodsAdapter(data?.tcMaps!!)
        rvGoods?.layoutManager = LinearLayoutManager(mContext)
        rvGoods?.adapter = orderGoodsAdapter
    }


    class OrderGoodsAdapter constructor(goods: MutableList<OrderDetailEntity.TCMap>)
        : BaseAdapter<OrderDetailEntity.TCMap, BaseViewHolder>(R.layout.item_order_detail_tc, goods) {
        override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.TCMap?) {
            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
                    ?.setText(R.id.tv_goods_price, "¥" + item?.goodsInfoPrice)
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