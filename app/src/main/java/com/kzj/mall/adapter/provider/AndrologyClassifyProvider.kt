package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.IHomeEntity

/**
 * 男科分类
 */
class AndrologyClassifyProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_classify
    }

    override fun viewType(): Int {
        return IHomeEntity.CLASSIFY
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
    }
}