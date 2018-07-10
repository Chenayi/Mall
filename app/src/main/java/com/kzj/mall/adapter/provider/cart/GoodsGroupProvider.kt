package com.kzj.mall.adapter.provider.cart

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.cart.GoodsGroupEntity
import com.kzj.mall.entity.cart.ICart

class GoodsGroupProvider : BaseItemProvider<GoodsGroupEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_group
    }

    override fun viewType(): Int {
        return ICart.GROUP
    }

    override fun convert(helper: BaseViewHolder?, data: GoodsGroupEntity?, position: Int) {
        val rvGroup = helper?.getView<RecyclerView>(R.id.rv_group)
        rvGroup?.layoutManager = LinearLayoutManager(mContext)
        val groupAdapter = GroupAdapter(data?.groups!!)
        rvGroup?.adapter = groupAdapter
    }

    inner class GroupAdapter constructor(val groupDatas : MutableList<GoodsGroupEntity.Group>)
        : BaseAdapter<GoodsGroupEntity.Group, BaseViewHolder>(R.layout.item_cart_group_item,groupDatas) {
        override fun convert(helper: BaseViewHolder?, item: GoodsGroupEntity.Group?) {
            helper?.setGone(R.id.line,helper?.layoutPosition > 0)
        }
    }
}