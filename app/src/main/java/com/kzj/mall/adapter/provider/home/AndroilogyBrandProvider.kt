package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.AndrologyBrandEntity
import com.kzj.mall.entity.home.IHomeEntity

class AndroilogyBrandProvider : BaseItemProvider<AndrologyBrandEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_brand
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_BRAND
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologyBrandEntity?, position: Int) {
    }
}