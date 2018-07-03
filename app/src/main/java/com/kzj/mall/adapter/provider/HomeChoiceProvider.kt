package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 公告/精选
 */
class HomeChoiceProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_choice
    }

    override fun viewType(): Int {
        return IHomeEntity.CHOICE
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
    }
}