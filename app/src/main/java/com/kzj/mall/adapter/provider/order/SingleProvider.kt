package com.kzj.mall.adapter.provider.order

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.order.DpMap
import com.kzj.mall.utils.FloatUtils
import com.kzj.mall.utils.PriceUtils

/**
 * 单品
 */
class SingleProvider : BaseItemProvider<DpMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail_dp

    override fun viewType() = 0

    override fun convert(helper: BaseViewHolder?, data: DpMap?, position: Int) {

        val goodsInfoPrice = FloatUtils.format(data?.goodsInfoSumPrice?.toFloat()!! / data?.goodsInfoNum?.toFloat()!!)

        helper?.setText(R.id.tv_goods_price, "¥" + goodsInfoPrice)
                ?.setText(R.id.tv_goods_num, "x" + data?.goodsInfoNum)
                ?.setText(R.id.tv_goods_name, data?.goodsInfoName)

        GlideApp.with(mContext)
                .load(data?.goodsImg)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}