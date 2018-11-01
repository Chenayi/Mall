package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.AndrologyBrandEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 男科品牌
 */
class AndroilogyBrandProvider : BaseItemProvider<AndrologyBrandEntity,BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_andrology_brand
    }

    override fun viewType(): Int {
        return IHomeEntity.MALE_BRAND
    }

    override fun convert(helper: BaseViewHolder?, data: AndrologyBrandEntity?, position: Int) {
        helper?.addOnClickListener(R.id.ll_brand_1)
                ?.addOnClickListener(R.id.ll_brand_2)
                ?.addOnClickListener(R.id.ll_brand_3)
                ?.addOnClickListener(R.id.ll_brand_4)
                ?.addOnClickListener(R.id.ll_brand_5)
                ?.addOnClickListener(R.id.ll_brand_6)
                ?.addOnClickListener(R.id.ll_brand_7)
                ?.addOnClickListener(R.id.ll_brand_8)
    }
}