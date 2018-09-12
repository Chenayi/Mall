package com.kzj.mall.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.order.IGoodsDetail
import com.kzj.mall.entity.order.OrderDetailEntity

class OrderDetailAdapter constructor(orderDatas: MutableList<IGoodsDetail>)
    : BaseAdapter<IGoodsDetail, BaseViewHolder>(R.layout.item_order_detail1, orderDatas) {
    override fun convert(helper: BaseViewHolder?, item: IGoodsDetail?) {

        //单品
        if (item is OrderDetailEntity.DPMap) {
            helper?.setGone(R.id.ll_top, false)
            val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
            val goods = ArrayList<OrderDetailEntity.Goods>()
            goods.add(item?.goods!!)
            val orderGoodsAdapter = OrderGoodsAdapter(goods)
            rvGoods?.layoutManager = LinearLayoutManager(mContext)
            rvGoods?.adapter = orderGoodsAdapter
        }

        //疗程
        else if (item is OrderDetailEntity.LCMap) {
            helper?.setGone(R.id.ll_top, true)
                    ?.setText(R.id.tv_tag, "疗程")
                    ?.setText(R.id.tv_combination_name, item?.combination?.combination_name)
            val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
            val goods = ArrayList<OrderDetailEntity.Goods>()
            goods.add(item?.goods!!)
            val orderGoodsAdapter = OrderGoodsAdapter(goods)
            rvGoods?.layoutManager = LinearLayoutManager(mContext)
            rvGoods?.adapter = orderGoodsAdapter
        }

        //套餐
        else if (item is OrderDetailEntity.TCMap) {
            helper?.setGone(R.id.ll_top, true)
                    ?.setText(R.id.tv_tag, "套餐")
                    ?.setText(R.id.tv_combination_name, item?.combination?.combination_name)
            val rvGoods = helper?.getView<RecyclerView>(R.id.rv_goods)
            val orderGoodsAdapter = OrderGoodsAdapter(item?.goods!!)
            rvGoods?.layoutManager = LinearLayoutManager(mContext)
            rvGoods?.adapter = orderGoodsAdapter
        }

    }

    class OrderGoodsAdapter constructor(goods: MutableList<OrderDetailEntity.Goods>)
        : BaseAdapter<OrderDetailEntity.Goods, BaseViewHolder>(R.layout.item_order_detail_goods, goods) {
        override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.Goods?) {
            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
                    ?.setText(R.id.tv_goods_price, "¥" + item?.goodsInfoSumPrice)
                    ?.setText(R.id.tv_goods_num, "x" + item?.goodsNum)
                    ?.setText(R.id.tv_goods_name, item?.goodsName)

            GlideApp.with(mContext)
                    .load(item?.goodsImg)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)
        }

    }
}