package com.kzj.mall.adapter

import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.GoodsDetailEntity

class GoodsDetailGroupAdapter
constructor(val groupDatas : MutableList<GoodsDetailEntity.Group>)
    : BaseAdapter<GoodsDetailEntity.Group,BaseViewHolder>(R.layout.item_goods_detail_group,groupDatas) {
    override fun convert(helper: BaseViewHolder?, item: GoodsDetailEntity.Group?) {
        val linearLayout = helper?.getView<LinearLayout>(R.id.ll_item)
        var params: RelativeLayout.LayoutParams = linearLayout?.layoutParams as RelativeLayout.LayoutParams

        params.leftMargin = SizeUtils.dp2px(10f)
        if (helper?.layoutPosition == datas?.size - 1) {
            params.rightMargin = SizeUtils.dp2px(10f)
            helper?.setGone(R.id.tv_plus,false)
        } else {
            params.rightMargin = 0
            helper?.setGone(R.id.tv_plus,true)
        }
        linearLayout.layoutParams = params
    }
}