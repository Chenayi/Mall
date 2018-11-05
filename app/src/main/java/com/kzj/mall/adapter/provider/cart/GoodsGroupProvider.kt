package com.kzj.mall.adapter.provider.cart

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.PriceUtils

class GoodsGroupProvider : BaseItemProvider<CartGroupEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_goods_list_group
    }

    override fun viewType(): Int {
        return ICart.GROUP
    }

    override fun convert(helper: BaseViewHolder?, data: CartGroupEntity?, position: Int) {
        val rvGroup = helper?.getView<RecyclerView>(R.id.rv_group)
        rvGroup?.setFocusableInTouchMode(false);
        rvGroup?.requestFocus();
        rvGroup?.layoutManager = LinearLayoutManager(mContext)
        val groupAdapter = GroupAdapter(data?.groups!!)
        rvGroup?.adapter = groupAdapter

        val allPrice = data?.goods_price?.toFloat()!!
        val num = data?.goods_num!!
        val singlePrice = allPrice / num
        helper?.setText(R.id.tv_combination_name, data?.combination_name)
                ?.setText(R.id.tv_group_price, PriceUtils.split11sp("¥" + FloatUtils.format(singlePrice)))
                ?.setText(R.id.tv_goods_num,"x"+data?.goods_num?.toString())
    }

    inner class GroupAdapter constructor(groupDatas: MutableList<CartGroupEntity.Group>)
        : BaseAdapter<CartGroupEntity.Group, BaseViewHolder>(R.layout.item_goods_group_item, groupDatas) {
        override fun convert(helper: BaseViewHolder?, item: CartGroupEntity.Group?) {
            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
                    ?.setText(R.id.tv_goods_price, "¥" + item?.c_goods?.goods_price)
                    ?.setText(R.id.tv_goods_name, item?.c_goods?.goods_name)
                    ?.setText(R.id.tv_goods_num, "x" + item?.goodsNum)

            GlideApp.with(mContext)
                    .load(item?.c_goods?.goods_img)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)
        }
    }
}