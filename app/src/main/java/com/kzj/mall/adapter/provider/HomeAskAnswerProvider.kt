package com.kzj.mall.adapter.provider

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 问答解惑
 */
class HomeAskAnswerProvider : BaseItemProvider<IHomeEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_ask_answer
    }

    override fun viewType(): Int {
        return IHomeEntity.ASK_ANSWER
    }

    override fun convert(helper: BaseViewHolder?, data: IHomeEntity?, position: Int) {
    }
}