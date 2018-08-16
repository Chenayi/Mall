package com.kzj.mall.adapter

import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.SearchEntity

class SearchGridAdapter constructor(val searchDatas: MutableList<SearchEntity>)
    : BaseAdapter<SearchEntity, BaseViewHolder>(R.layout.item_search_grid, searchDatas) {
    override fun convert(helper: BaseViewHolder?, item: SearchEntity?) {
        val layoutPosition = helper?.layoutPosition
        val layout = helper?.getView<LinearLayout>(R.id.ll_container)
        if (layoutPosition!! % 2  != 0){
            layout?.setPadding(SizeUtils.dp2px(10f),0,SizeUtils.dp2px(10f),0)
        }else{
            layout?.setPadding(SizeUtils.dp2px(10f),0,0,0)
        }
    }
}