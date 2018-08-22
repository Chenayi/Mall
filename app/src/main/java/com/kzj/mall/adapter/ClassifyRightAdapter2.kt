package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ClassifyRightEntity

class ClassifyRightAdapter2(val classifyRightEntitys: MutableList<ClassifyRightEntity.CateList>)
    : BaseAdapter<ClassifyRightEntity.CateList, BaseViewHolder>(R.layout.item_classify_right, classifyRightEntitys) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyRightEntity.CateList?) {
        helper?.setText(R.id.tv_classify, item?.cat_name)
                ?.setGone(R.id.line_right, helper?.layoutPosition!! % 2 != 0)
    }
}