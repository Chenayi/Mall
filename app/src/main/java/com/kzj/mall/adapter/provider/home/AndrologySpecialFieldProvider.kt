package com.kzj.mall.adapter.provider.home

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.AndrologySpecialFieldEntity
import com.kzj.mall.entity.home.IHomeEntity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import com.takusemba.multisnaprecyclerview.OnSnapListener

/**
 * 男科专场
 */
class AndrologySpecialFieldProvider : BaseItemProvider<AndrologySpecialFieldEntity, BaseViewHolder>() {
    var rv: MultiSnapRecyclerView? = null

    override fun layout(): Int {
        return R.layout.item_andrology_special_field_list
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_SEPCIAL_FIELD
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologySpecialFieldEntity?, position: Int) {
        rv = helper?.getView(R.id.rv)
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        rv?.setLayoutManager(layoutManager)

        data?.specialFields?.let {
            val myAdapter = MyAdapter(it)
            rv?.setAdapter(myAdapter)
        }
        data?.p?.let {
            rv?.scrollToPosition(it)
        }
        rv?.setOnSnapListener(object :OnSnapListener{
            override fun snapped(position: Int) {
                data?.p = position
            }
        })
    }

    inner class MyAdapter
    constructor(val aDatas: MutableList<AndrologySpecialFieldEntity.SpecialFields>)
        : BaseAdapter<AndrologySpecialFieldEntity.SpecialFields, BaseViewHolder>(R.layout.item_andrology_special_field, aDatas) {
        override fun convert(helper: BaseViewHolder?, item: AndrologySpecialFieldEntity.SpecialFields?) {
            val linearLayout = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = linearLayout?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(10f)
            if (helper?.layoutPosition == datas?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(10f)
            } else {
                params.rightMargin = 0
            }
            linearLayout.layoutParams = params
        }
    }
}