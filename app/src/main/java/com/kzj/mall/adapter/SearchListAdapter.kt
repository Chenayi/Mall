package com.kzj.mall.adapter

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.SearchEntity

class SearchListAdapter constructor(val searchDatas: MutableList<SearchEntity>)
    : BaseAdapter<SearchEntity, BaseViewHolder>(R.layout.item_search_list, searchDatas) {
    override fun convert(helper: BaseViewHolder?, item: SearchEntity?) {
        helper?.setGone(R.id.line_top,helper?.layoutPosition == 0)
                ?.setGone(R.id.line_bottom,helper?.layoutPosition < data?.size-1)
    }
}