package com.kzj.mall.adapter.provider.cart

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.C
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.cart.CartGroupEntity
import com.kzj.mall.entity.cart.ICart
import com.kzj.mall.ui.activity.GoodsDetailActivity

class CartGroupProvider : BaseItemProvider<CartGroupEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_cart_group
    }

    override fun viewType(): Int {
        return ICart.GROUP
    }

    override fun convert(helper: BaseViewHolder?, data: CartGroupEntity?, p1: Int) {

        val ivCheckDelete = helper?.getView<ImageView>(R.id.iv_check)
        //删除模式
        if (data?.isDeleteMode == true) {
            ivCheckDelete?.setImageResource(R.mipmap.del)
        }
        //正常选择模式
        else {
            ivCheckDelete?.setImageResource(if (data?.isCheck == true) R.mipmap.icon_cart_check else R.mipmap.check_nor)
        }
        helper?.addOnClickListener(R.id.iv_check)
                ?.addOnClickListener(R.id.iv_minus)
                ?.addOnClickListener(R.id.iv_plus)
                ?.setText(R.id.tv_goods_pre_price, "已省 ¥" + data?.goods_pre_price)
                ?.setText(R.id.tv_combination_name, data?.combination_name)
                ?.setText(R.id.tv_goods_num, data?.goods_num?.toString()?.trim())

        val ivMinus = helper?.getView<ImageView>(R.id.iv_minus)
        val ivPlus = helper?.getView<ImageView>(R.id.iv_plus)
        data?.goods_num?.let {
            if (it <= 1) {
                ivMinus?.isEnabled = false
                ivMinus?.setImageResource(R.mipmap.minus_nor)
            } else {
                ivMinus?.isEnabled = true
                ivMinus?.setImageResource(R.mipmap.minus_cart)
            }
            val goodsStock = data?.goods_stock
            ivPlus?.isEnabled = it < goodsStock!!
        }

        val rvGroup = helper?.getView<RecyclerView>(R.id.rv_group)
        rvGroup?.setFocusableInTouchMode(false);
        rvGroup?.requestFocus();
        rvGroup?.layoutManager = LinearLayoutManager(mContext)
        val groupAdapter = GroupAdapter(data?.groups!!)
        groupAdapter?.setOnItemClickListener { adapter, view, position ->
            val intent = Intent(mContext, GoodsDetailActivity::class.java)
            intent?.putExtra(C.GOODS_INFO_ID, data?.groups?.get(position)?.c_goods?.goods_info_id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext?.startActivity(intent)
        }
        rvGroup?.adapter = groupAdapter
    }

    inner class GroupAdapter constructor(groupDatas: MutableList<CartGroupEntity.Group>)
        : BaseAdapter<CartGroupEntity.Group, BaseViewHolder>(R.layout.item_cart_group_item, groupDatas) {
        override fun convert(helper: BaseViewHolder?, item: CartGroupEntity.Group?) {
            helper?.setGone(R.id.line, helper?.layoutPosition > 0)
                    ?.setText(R.id.tv_goods_price, "¥" + item?.c_goods?.goods_price)
                    ?.setText(R.id.tv_goods_name, item?.c_goods?.goods_name)
                    ?.setText(R.id.tv_goods_num,"x"+item?.goodsNum)

            GlideApp.with(mContext)
                    .load(item?.c_goods?.goods_img)
                    .placeholder(R.color.gray_default)
                    .centerCrop()
                    .into(helper?.getView(R.id.iv_goods)!!)
        }
    }
}