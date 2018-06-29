package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.IHomeEntity

/**
 * 男科广告banner
 */
class AndrologyAdvBannerProvider : BaseItemProvider<IHomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_adv_banner
    }

    override fun viewType(): Int {
        return IHomeEntity.ADV_BANNER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
    }
}