package com.kzj.mall.adapter.provider.home

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
        helper?.addOnClickListener(R.id.iv_yp1)
                ?.addOnClickListener(R.id.iv_yp2)
                ?.addOnClickListener(R.id.iv_yp3)
                ?.addOnClickListener(R.id.iv_yp4)
                ?.addOnClickListener(R.id.iv_yp5)
                ?.addOnClickListener(R.id.iv_yp6)
                ?.addOnClickListener(R.id.iv_yp7)
                ?.addOnClickListener(R.id.iv_yp8)
                ?.addOnClickListener(R.id.ll_yp9)
                ?.addOnClickListener(R.id.ll_yp10)
                ?.addOnClickListener(R.id.ll_yp11)
                ?.addOnClickListener(R.id.ll_yp12)
    }
}