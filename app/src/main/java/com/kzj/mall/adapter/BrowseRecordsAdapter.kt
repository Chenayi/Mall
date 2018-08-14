package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.BrowseRecordEntity

class BrowseRecordsAdapter constructor(val browseRecords: MutableList<BrowseRecordEntity>)
    : BaseAdapter<BrowseRecordEntity, BaseViewHolder>(R.layout.item_browse_record, browseRecords) {
    override fun convert(helper: BaseViewHolder?, item: BrowseRecordEntity?) {
        helper?.setGone(R.id.iv_delete, item?.deleteMode == true)
    }
}