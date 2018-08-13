package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.OrderDetailEntity

class OrderDetailAdapter constructor(val orderDatas : MutableList<OrderDetailEntity>)
    :BaseAdapter<OrderDetailEntity,BaseViewHolder>(R.layout.item_order_detail1,orderDatas) {
    override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity?) {

    }

    class OrderGoodsAdapter constructor(val goods:MutableList<OrderDetailEntity.OrderGoods>)
        :BaseAdapter<OrderDetailEntity.OrderGoods,BaseViewHolder>(R.layout.item_order_detail_goods,goods){
        override fun convert(helper: BaseViewHolder?, item: OrderDetailEntity.OrderGoods?) {
            
        }

    }
}