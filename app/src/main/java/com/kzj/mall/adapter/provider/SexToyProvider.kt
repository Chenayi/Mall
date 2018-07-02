package com.kzj.mall.adapter.provider

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.adapter.BaseAdapter
import com.kzj.mall.entity.IHomeEntity
import com.kzj.mall.entity.SexToyEntity

/**
 * 情趣用品
 */
class SexToyProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_sex_toy_list
    }

    override fun viewType(): Int {
        return IHomeEntity.SEX_TOY
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
        var rv = helper?.getView<RecyclerView>(R.id.rv_sex_toy_sale)
        val myAdapter = MyAdapter(getDatas())
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false)
        rv?.setLayoutManager(layoutManager)
        rv?.setAdapter(myAdapter)
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

        }
    }
}