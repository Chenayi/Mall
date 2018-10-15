package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.kzj.mall.adapter.provider.order.LcProvider
import com.kzj.mall.adapter.provider.order.SingleProvider
import com.kzj.mall.adapter.provider.order.TcProvider
import com.kzj.mall.entity.order.IGoodsDetail

class OrderDetailAdapter(orderDatas: MutableList<IGoodsDetail>) : MultipleItemRvAdapter<IGoodsDetail, BaseViewHolder>(orderDatas) {

    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate?.registerProvider(SingleProvider())
        mProviderDelegate?.registerProvider(LcProvider())
        mProviderDelegate?.registerProvider(TcProvider())
    }

    override fun getViewType(t: IGoodsDetail) = t.type()
}