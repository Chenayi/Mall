package com.kzj.mall.adapter.provider.order

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.order.LcMap

/**
 * 疗程
 */
class LcProvider : BaseItemProvider<LcMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail_lc

    override fun viewType() = 2

    override fun convert(helper: BaseViewHolder?, data: LcMap?, position: Int) {
        helper?.setText(R.id.tv_goods_price, "¥" + data?.goodsInfoPrice)
                ?.setText(R.id.tv_goods_num, "x" + data?.goodsInfoNum)
                ?.setText(R.id.tv_goods_name, data?.goodsInfoName)
                ?.setText(R.id.tv_combination_name, data?.goodsMarketingName)

        GlideApp.with(mContext)
                .load(data?.goodsImg)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}