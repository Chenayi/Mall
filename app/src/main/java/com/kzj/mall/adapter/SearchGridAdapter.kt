package com.kzj.mall.adapter

import android.graphics.Paint
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.SearchEntity

class SearchGridAdapter constructor(val searchDatas: MutableList<SearchEntity.Data>)
    : BaseAdapter<SearchEntity.Data, BaseViewHolder>(R.layout.item_search_grid, searchDatas) {
    override fun convert(helper: BaseViewHolder?, item: SearchEntity.Data?) {
        val layoutPosition = helper?.layoutPosition
        helper?.setGone(R.id.view_top, layoutPosition == 0 || layoutPosition == 1)
                ?.setGone(R.id.view_right, layoutPosition!! % 2 != 0)
        val ivGoods = helper?.getView<ImageView>(R.id.iv_goods)
        val goodsImageViewWidth = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(70f)) / 2f
        val layoutParams = ivGoods?.layoutParams as LinearLayout.LayoutParams
        layoutParams?.width = goodsImageViewWidth.toInt()
        layoutParams?.height = goodsImageViewWidth.toInt()
        ivGoods?.requestLayout()

        helper?.setText(R.id.tv_goods_name, item?.goods_name)
                ?.setText(R.id.tv_goods_indication, item?.goods_indication)
                ?.setText(R.id.tv_goods_price, "¥" + item?.goods_price)
                ?.setText(R.id.tv_goods_market_price, "¥" + item?.goods_market_price)

        helper?.getView<TextView>(R.id.tv_goods_market_price)?.getPaint()?.setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线

        GlideApp.with(mContext)
                .load(item?.goods_img)
                .centerCrop()
                .placeholder(R.color.gray_default)
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}