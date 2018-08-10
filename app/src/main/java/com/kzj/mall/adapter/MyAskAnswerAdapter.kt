package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ask.AskAnswerEntity

class MyAskAnswerAdapter constructor(askAnswers: MutableList<AskAnswerEntity>)
    : BaseAdapter<AskAnswerEntity, BaseViewHolder>(R.layout.item_my_ask_answer, askAnswers) {
    override fun convert(helper: BaseViewHolder?, item: AskAnswerEntity?) {

    }
}