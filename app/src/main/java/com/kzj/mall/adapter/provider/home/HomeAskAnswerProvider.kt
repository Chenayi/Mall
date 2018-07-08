package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
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
        val tvAskNum = helper?.getView<TextView>(R.id.tv_ask_num)

        var leftText = "昨日有"
        var numText = "[1394]"
        var rightText = "条咨询得到解答"

        var spannableString = SpannableString(leftText + numText + rightText)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#731FCA")) ,
                leftText.length,leftText.length+numText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvAskNum?.setText(spannableString)
    }
}