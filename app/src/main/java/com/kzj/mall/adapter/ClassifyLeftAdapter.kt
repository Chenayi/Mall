package com.kzj.mall.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ClassifyLeftEntity

class ClassifyLeftAdapter
constructor(val bean: MutableList<ClassifyLeftEntity>)
    : BaseAdapter<ClassifyLeftEntity, BaseViewHolder>(R.layout.item_classify_left, bean) {
    override fun convert(helper: BaseViewHolder?, item: ClassifyLeftEntity?) {
        helper?.setText(R.id.tv_classify_name, item?.name)
                ?.setBackgroundColor(R.id.rl_item,
                        if (item?.isChoose == true) ContextCompat.getColor(mContext, R.color.gray_default)
                        else Color.WHITE)
        val tvName = helper?.getView<TextView>(R.id.tv_classify_name)
        item?.isChoose?.let {
            tvName?.paint?.isFakeBoldText = it
            helper?.setGone(R.id.icon_left,it)
        }

    }
}