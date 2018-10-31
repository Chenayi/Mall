package com.kzj.mall.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import com.blankj.utilcode.util.SizeUtils

class PriceUtils {
    companion object {
        fun format(amount: String): SpannableStringBuilder {
            val builder = SpannableStringBuilder(amount);
            builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(15f)), amount.indexOf("¥"), amount.indexOf("¥") + 1
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(15f)), amount.indexOf("."), amount?.length
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return builder
        }

        fun split12sp(amount: String): SpannableStringBuilder {
            val builder = SpannableStringBuilder(amount);
            builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(12f)), amount.indexOf("¥"), amount.indexOf("¥") + 1
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(12f)), amount.indexOf("."), amount?.length
                    , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            return builder
        }
    }
}