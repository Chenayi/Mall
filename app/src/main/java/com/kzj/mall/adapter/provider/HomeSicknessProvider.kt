package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeSicknessEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 常见疾病
 */
class HomeSicknessProvider : BaseItemProvider<HomeSicknessEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_sickness
    }

    override fun viewType(): Int {
        return IHomeEntity.SICKNESS
    }

    override fun convert(helper: BaseViewHolder?, data: HomeSicknessEntity?, position: Int) {
    }
}