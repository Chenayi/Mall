package com.kzj.mall.adapter

import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.kzj.mall.R
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.widget.CenterAlignImageSpan



class AskAnswerDetailAdapter constructor(val askDetails: MutableList<AskAnswerDetailEntity>)
    : BaseAdapter<AskAnswerDetailEntity, BaseViewHolder>(R.layout.item_ask_answer_detail, askDetails) {
    override fun convert(helper: BaseViewHolder?, item: AskAnswerDetailEntity?) {
        val tvContent = helper?.getView<TextView>(R.id.tv_content)
        val content = "  脸上的痘痘总是很难根治，并且是油性，用什么药能够帮助用什么 药能够帮助祛？"
        val sp = SpannableString(content)

        val drawable = ContextCompat.getDrawable(mContext,R.drawable.icon);
        drawable?.setBounds(0, 0, drawable?.getMinimumWidth(), drawable.getMinimumHeight());
        val imageSpan = CenterAlignImageSpan(drawable)
        sp.setSpan(imageSpan, 0, 1, ImageSpan.ALIGN_BASELINE);

        tvContent?.setText(sp)
    }
}