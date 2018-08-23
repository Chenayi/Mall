package com.kzj.mall.adapter.provider.home

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeBrandEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 品牌专区
 */
class HomeBrandProvider : BaseItemProvider<HomeBrandEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_brand
    }

    override fun viewType(): Int {
        return IHomeEntity.BRAND
    }

    override fun convert(helper: BaseViewHolder?, data: HomeBrandEntity?, position: Int) {
        helper?.addOnClickListener(R.id.ll_brand_1)
                ?.addOnClickListener(R.id.ll_brand_2)
                ?.addOnClickListener(R.id.ll_brand_3)
                ?.addOnClickListener(R.id.ll_brand_4)
                ?.addOnClickListener(R.id.ll_brand_5)
                ?.addOnClickListener(R.id.ll_brand_6)
                ?.addOnClickListener(R.id.ll_brand_7)
                ?.addOnClickListener(R.id.ll_brand_8)
                ?.addOnClickListener(R.id.ll_brand_9)
                ?.addOnClickListener(R.id.ll_brand_10)
                ?.addOnClickListener(R.id.ll_brand_11)
                ?.addOnClickListener(R.id.ll_brand_12)
    }
}