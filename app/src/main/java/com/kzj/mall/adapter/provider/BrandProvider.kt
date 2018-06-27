package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.HomeEntity

class BrandProvider : BaseItemProvider<HomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_brand
    }

    override fun viewType(): Int {
        return HomeEntity.BRAND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeEntity?, position: Int) {

    }
}