package com.kzj.mall.adapter.provider.cart

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.adapter.CartAdapter
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.ICart

class CartGroupProvider constructor(val carAdapter : CartAdapter): BaseItemProvider<CartGroupEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_cart_group
    }

    override fun viewType(): Int {
       return ICart.GROUP
    }

    override fun convert(helper: BaseViewHolder?, data: CartGroupEntity?, p1: Int) {
        val rvGroup = helper?.getView<RecyclerView>(R.id.rv_group)
        rvGroup?.layoutManager = LinearLayoutManager(mContext)
        val groupAdapter = GroupAdapter(data?.groups!!)
        rvGroup?.adapter = groupAdapter
        helper?.setVisible(R.id.iv_check,data?.isCheck!!)
        groupAdapter?.setOnItemClickListener { adapter, view, p2 ->
            data?.isCheck = !data?.isCheck
            carAdapter.notifyItemChanged(p1)
        }
    }

    inner class GroupAdapter constructor(val groupDatas : MutableList<CartGroupEntity.Group>)
        : BaseAdapter<CartGroupEntity.Group,BaseViewHolder>(R.layout.item_cart_group_item,groupDatas) {
        override fun convert(helper: BaseViewHolder?, item: CartGroupEntity.Group?) {
            helper?.setGone(R.id.line,helper?.layoutPosition > 0)
        }
    }
}