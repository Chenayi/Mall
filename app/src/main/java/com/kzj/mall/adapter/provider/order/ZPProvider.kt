package com.kzj.mall.adapter.provider.order

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.order.ZPMap

/**
 * 买赠
 */
class ZPProvider : BaseItemProvider<ZPMap, BaseViewHolder>() {
    override fun layout() = R.layout.item_order_detail_zp

    override fun viewType() = 5

    override fun convert(helper: BaseViewHolder?, data: ZPMap?, position: Int) {
        GlideApp.with(mContext)
                .load(data?.goodsImg)
                .placeholder(R.color.gray_f0)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods) as ImageView)

        helper?.setText(R.id.tv_goods_name, data?.goodsInfoName)
                ?.setText(R.id.tv_goods_num, "x${data?.goodsInfoNum}")
    }
}