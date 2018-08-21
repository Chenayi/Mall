package com.kzj.mall.adapter.provider.home

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.kzj.mall.R
import com.kzj.mall.entity.home.HomeAskAnswerEntity
import com.kzj.mall.entity.home.IHomeEntity

/**
 * 问答解惑
 */
class HomeAskAnswerProvider : BaseItemProvider<HomeAskAnswerEntity, BaseViewHolder>() {
    override fun layout(): Int {
        return R.layout.item_home_ask_answer
    }

    override fun viewType(): Int {
        return IHomeEntity.ASK_ANSWER
    }

    override fun convert(helper: BaseViewHolder?, data: HomeAskAnswerEntity?, position: Int) {
//        val tvAskNum = helper?.getView<TextView>(R.id.tv_ask_num)
//        var leftText = "昨日有"
//        var numText = "[1394]"
//        var rightText = "条咨询得到解答"
//        var spannableString = SpannableString(leftText + numText + rightText)
//        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#731FCA")) ,
//                leftText.length,leftText.length+numText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        tvAskNum?.setText(spannableString)


        val askList = data?.askList
        askList?.let {
            if (it.size > 0) {
                helper?.setText(R.id.tv_qname1, it.get(0).qName)
                        ?.setText(R.id.tv_username_day1, "来自:" + it.get(0).userName + " / 3天前")
            }
            if (it.size > 1) {
                helper?.setText(R.id.tv_qname2, it.get(1).qName)
                        ?.setText(R.id.tv_username_day2, "来自:" + it.get(1).userName + " / 3天前")
            }
            if (it.size > 2) {
                helper?.setText(R.id.tv_qname3, it.get(2).qName)
                        ?.setText(R.id.tv_username_day3, "来自:" + it.get(2).userName + " / 3天前")
            }
            if (it.size > 3) {
                helper?.setText(R.id.tv_qname4, it.get(3).qName)
                        ?.setText(R.id.tv_username_day4, "来自:" + it.get(3).userName + " / 3天前")
            }
            if (it.size > 4) {
                helper?.setText(R.id.tv_qname5, it.get(4).qName)
                        ?.setText(R.id.tv_username_day5, "来自:" + it.get(4).userName + " / 3天前")
            }
        }
    }
}