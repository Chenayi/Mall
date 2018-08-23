package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.AndrologyStationEntity
import com.kzj.mall.entity.home.IHomeEntity

class AndrologyStationProvider : BaseItemProvider<AndrologyStationEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_male_station
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_STATION
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologyStationEntity?, position: Int) {
        helper?.addOnClickListener(R.id.ll_man_station_1)
                ?.addOnClickListener(R.id.ll_man_station_2)
                ?.addOnClickListener(R.id.ll_man_station_3)
                ?.addOnClickListener(R.id.ll_man_station_4)
                ?.addOnClickListener(R.id.ll_man_station_5)
                ?.addOnClickListener(R.id.ll_man_station_6)
                ?.addOnClickListener(R.id.ll_man_station_7)
    }
}