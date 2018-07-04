package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeChoiceGoodsEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 精选优品
 */
class HomeChoiceGoodsProvider : BaseItemProvider<HomeChoiceGoodsEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_choice_goods
    }

    override fun viewType(): Int {
        return IHomeEntity.CHOICE_GOODS
    }

    override fun convert(helper: BaseViewHolder?, data: HomeChoiceGoodsEntity?, position: Int) {

    }
}