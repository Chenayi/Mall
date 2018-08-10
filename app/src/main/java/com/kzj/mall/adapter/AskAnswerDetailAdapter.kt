package com.kzj.mall.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ask.AskAnswerDetailEntity

class AskAnswerDetailAdapter constructor(val askDetails:MutableList<AskAnswerDetailEntity>)
    : BaseAdapter<AskAnswerDetailEntity,BaseViewHolder>(R.layout.item_ask_answer_detail,askDetails) {
    override fun convert(helper: BaseViewHolder?, item: AskAnswerDetailEntity?) {
    }
}