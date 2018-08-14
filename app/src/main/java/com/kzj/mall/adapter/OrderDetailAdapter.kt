package com.kzj.mall.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.OrderDetailEntity

class OrderDetailAdapter constructor(val orderDatas: MutableList<OrderDetailEntity.OrderGoods>)
    : BaseAdapter<OrderDetailEntity.OrderGoods, BaseViewHolder>(R.layout.item_order_detail1, orderDatas) {
    override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.OrderGoods?) {
        helper?.setGone(R.id.ll_top, item?.type != OrderDetailEntity.TYPE_SINGLE)

        if (item?.type == OrderDetailEntity.TYPE_COURSE) {
            helper?.setText(R.id.tv_tag, "疗程")
        } else if (item?.type == OrderDetailEntity.TYPE_GROUP) {
            helper?.setText(R.id.tv_tag, "套餐")
        }

        val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
        val orderGoodsAdapter = OrderGoodsAdapter(item?.goods!!)
        rvGoods?.layoutManager = LinearLayoutManager(mContext)
        rvGoods?.adapter = orderGoodsAdapter
    }

    class OrderGoodsAdapter constructor(val goods: MutableList<OrderDetailEntity.Goods>)
        : BaseAdapter<OrderDetailEntity.Goods, BaseViewHolder>(R.layout.item_order_detail_goods, goods) {
        override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.Goods?) {
            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
        }

    }
}