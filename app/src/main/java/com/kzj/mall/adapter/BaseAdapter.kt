package com.kzj.mall.adapter

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yatoooon.screenadaptation.ScreenAdapterTools

abstract class BaseAdapter<T, K : BaseViewHolder>
constructor(layoutResId: Int, datas: MutableList<T>) : BaseQuickAdapter<T, K>(layoutResId, datas) {

    override fun getItemView(layoutResId: Int, parent: ViewGroup?): View {
        val view = super.getItemView(layoutResId, parent)
        ScreenAdapterTools.getInstance().loadView(view as ViewGroup)
        return view
    }
}