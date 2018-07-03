package com.kzj.mall.adapter.provider

import android.support.v7.widget.LinearLayoutManager
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
        val myAdapter = MyAdapter(getDatas())
        val layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        rv?.setLayoutManager(layoutManager)
        rv?.setAdapter(myAdapter)
        data?.p?.let {
            rv?.scrollToPosition(it)
        }
        rv?.setOnSnapListener(object :OnSnapListener{
            override fun snapped(position: Int) {
                data?.p = position
            }
        })
    }

    private fun getDatas(): MutableList<AndrologySpecialFieldEntity> {
        var datas = ArrayList<AndrologySpecialFieldEntity>()
        for (i in 0..8) {
            datas?.add(AndrologySpecialFieldEntity())
        }
        return datas
    }

    inner class MyAdapter
    constructor(val aDatas: MutableList<AndrologySpecialFieldEntity>)
        : BaseAdapter<AndrologySpecialFieldEntity, BaseViewHolder>(R.layout.item_andrology_special_field, aDatas) {
        override fun convert(helper: BaseViewHolder?, item: AndrologySpecialFieldEntity?) {

        }
    }
}