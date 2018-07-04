package com.kzj.mall.adapter

import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ClassifyRightEntity
import com.kzj.mall.entity.ClassifyRightSectionEntity

class ClassifyRightAdapter : BaseSectionQuickAdapter<ClassifyRightSectionEntity, BaseViewHolder> {
    constructor(layoutResId: Int, sectionHeadResId: Int, data: MutableList<ClassifyRightSectionEntity>?) : super(layoutResId, sectionHeadResId, data)

    override fun convertHead(helper: BaseViewHolder?, item: ClassifyRightSectionEntity?) {
        val llHeader = helper?.getView<LinearLayout>(R.id.ll_header)
        val position = helper?.layoutPosition
        if (position == 1) {
            llHeader?.setBackgroundColor(Color.WHITE)
        } else {
            llHeader?.setBackgroundResource(R.drawable.top_white_corners_8)
        }
    }

    override fun convert(helper: BaseViewHolder?, item: ClassifyRightSectionEntity?) {
        val rvClassifyContent = helper?.getView<RecyclerView>(R.id.rv_classify_content)
        val gridLayoutManager = GridLayoutManager(mContext, 3)
        rvClassifyContent?.layoutManager = gridLayoutManager
        item?.t?.let {
            val adapter = ClassifyContentAdapter(R.layout.item_classify_content, it)
            rvClassifyContent?.adapter = adapter
        }
    }

    inner class ClassifyContentAdapter : BaseAdapter<ClassifyRightEntity, BaseViewHolder> {
        constructor(layoutResId: Int, datas: MutableList<ClassifyRightEntity>) : super(layoutResId, datas)

        override fun convert(helper: BaseViewHolder?, item: ClassifyRightEntity?) {

        }

    }

}