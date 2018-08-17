package com.kzj.mall.adapter

import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.SearchEntity

class SearchListAdapter constructor(val searchDatas: MutableList<SearchEntity.Data>)
    : BaseAdapter<SearchEntity.Data, BaseViewHolder>(R.layout.item_search_list, searchDatas) {
    override fun convert(helper: BaseViewHolder?, item: SearchEntity.Data?) {
        helper?.setGone(R.id.line_top, helper?.layoutPosition == 0)
                ?.setGone(R.id.line_bottom, helper?.layoutPosition < data?.size - 1)
                ?.setText(R.id.tv_goods_name, item?.goods_name)
                ?.setText(R.id.tv_goods_indication, item?.goods_indication)
                ?.setText(R.id.tv_goods_price, "￥" + item?.goods_price)
                ?.setText(R.id.tv_goods_market_price, "￥" + item?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.getPaint()?.setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线

        GlideApp.with(mContext)
                .load(item?.goods_img)
                .centerCrop()
                .placeholder(R.color.gray_default)
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}