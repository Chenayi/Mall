package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.GlideApp
import com.kzj.mall.R
import com.kzj.mall.entity.BrowseRecordEntity

class BrowseRecordsAdapter constructor(browseRecords: MutableList<BrowseRecordEntity.BrowserecordList>)
    : BaseAdapter<BrowseRecordEntity.BrowserecordList, BaseViewHolder>(R.layout.item_browse_record, browseRecords) {

    var deleteMode = false

    override fun convert(helper: BaseViewHolder?, item: BrowseRecordEntity.BrowserecordList?) {
        helper?.setGone(R.id.iv_check, deleteMode  == true)
                ?.setText(R.id.tv_goods_price, "¥" + item?.goods?.goodsPrice)
                ?.setText(R.id.tv_goods_name, item?.goods?.goodsName)
                ?.setText(R.id.tv_goods_info, item?.goods?.goodsInfoSubtitle)
                ?.addOnClickListener(R.id.iv_check)
                ?.setImageResource(R.id.iv_check,if (item?.check == true) R.mipmap.icon_cart_check else R.mipmap.check_nor)

        GlideApp.with(mContext)
                .load(item?.goods?.goodsImg)
                .placeholder(R.color.gray_default)
                .centerCrop()
                .into(helper?.getView(R.id.iv_goods)!!)
    }
}