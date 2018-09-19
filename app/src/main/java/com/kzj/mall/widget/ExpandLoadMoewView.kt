package com.kzj.mall.widget

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.kzj.mall.R

class ExpandLoadMoewView : LoadMoreView {
    private var endTips: String? = null

    constructor()
    constructor(endTips: String){
        this.endTips = endTips
    }

    override fun convert(holder: BaseViewHolder?) {
        super.convert(holder)
        this.endTips?.let {
            setEndTips(holder)
        }
    }

    fun setEndTips(holder: BaseViewHolder?) {
        holder?.setText(getEndTipsId(), endTips)
    }

    override fun getLayoutId(): Int {
        return R.layout.load_more_view
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }

    fun getEndTipsId(): Int {
        return R.id.tv_end_tips
    }

    fun getEndIconId(): Int {
        return R.id.iv_end_icon
    }
}