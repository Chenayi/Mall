package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeChoiceEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 小康公告
 */
class HomeChoiceProvider : BaseItemProvider<HomeChoiceEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_choice
    }

    override fun viewType(): Int {
        return IHomeEntity.CHOICE
    }

    override fun convert(helper: BaseViewHolder?, data: HomeChoiceEntity?, position: Int) {
        helper?.setText(R.id.tv_title,data?.promotionalAd?.wap_promotional_title)
    }
}