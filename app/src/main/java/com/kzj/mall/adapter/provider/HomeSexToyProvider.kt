package com.kzj.mall.adapter.provider

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.home.IHomeEntity
import com.kzj.mall.entity.SexToyEntity
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView

/**
 * 情趣用品
 */
class HomeSexToyProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    var isInitialized = false
    var rv: MultiSnapRecyclerView? = null

    override fun layout(): Int {
        return R.layout.item_home_sex_toy_list
    }

    override fun viewType(): Int {
        return IHomeEntity.SEX_TOY
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        if (isInitialized == false) {
            rv = helper?.getView(R.id.rv_sex_toy_sale)
            val myAdapter = MyAdapter(getDatas())
            val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            rv?.setLayoutManager(layoutManager)
            rv?.setAdapter(myAdapter)
            isInitialized = true
        }
    }


    private fun getDatas(): MutableList<SexToyEntity> {
        var datas = ArrayList<SexToyEntity>()
        for (i in 0..8) {
            datas?.add(SexToyEntity())
        }
        return datas
    }

    inner class MyAdapter
    constructor(val sexToyDatas: MutableList<SexToyEntity>)
        : BaseAdapter<SexToyEntity, BaseViewHolder>(R.layout.item_home_sex_toy, sexToyDatas) {
        override fun convert(helper: BaseViewHolder?, item: SexToyEntity?) {
            val linearLayout = helper?.getView<LinearLayout>(R.id.ll_item)
            var params: RelativeLayout.LayoutParams = linearLayout?.layoutParams as RelativeLayout.LayoutParams

            params.leftMargin = SizeUtils.dp2px(12f)
            if (helper?.layoutPosition == datas?.size - 1) {
                params.rightMargin = SizeUtils.dp2px(12f)
            } else {
                params.rightMargin = 0
            }
            linearLayout.layoutParams = params
        }
    }
}