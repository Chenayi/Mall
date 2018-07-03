package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity
import com.kzj.mall.entity.home.IHomeEntity

class AndrologyStationProvider : BaseItemProvider<HomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_male_station
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_STATION
    }

    override fun convert(helper: BaseViewHolder?, data: HomeEntity?, position: Int) {
    }
}