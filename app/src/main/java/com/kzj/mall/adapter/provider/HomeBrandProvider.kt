package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 品牌专区
 */
class HomeBrandProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_brand
    }

    override fun viewType(): Int {
        return IHomeEntity.BRAND
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {

    }
}