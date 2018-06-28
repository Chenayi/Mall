package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.IHomeEntity

class ChoiceGoodsProvider : BaseItemProvider<IHomeEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_choice_goods
    }

    override fun viewType(): Int {
        return IHomeEntity.CHOICE_GOODS
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {

    }
}