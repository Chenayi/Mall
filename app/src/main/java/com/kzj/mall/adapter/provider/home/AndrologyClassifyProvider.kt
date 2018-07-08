package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.AndrologyClassifyEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 男科分类
 */
class AndrologyClassifyProvider : BaseItemProvider<AndrologyClassifyEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_classify
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_CLASSIFY
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologyClassifyEntity?, position: Int) {
    }
}