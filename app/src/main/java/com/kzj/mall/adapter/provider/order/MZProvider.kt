package com.kzj.mall.adapter.provider.order

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.order.MZMap

/**
 * 买赠
 */
class MZProvider : BaseItemProvider<MZMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail_mz

    override fun viewType() = 4

    override fun convert(helper: BaseViewHolder?, data: MZMap?, position: Int) {
        helper?.setText(R.id.tv_combination_name, data?.goodsMarketingName)
                ?.setText(R.id.tv_goods_name, data?.goodsInfoName)
                ?.setText(R.id.tv_goods_num, "x${data?.goodsInfoNum}")

        GlideApp.with(mContext)
                .load(data?.goodsImg)
                ?.placeholder(R.color.gray_f0)
                ?.centerCrop()
                ?.into(helper?.getView(R.id.iv_goods) as ImageView)
    }
}