package com.kzj.mall.utils

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import com.blankj.utilcode.util.SizeUtils
import java.math.BigDecimal
import java.util.*

object PriceUtils {
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

    fun split11sp(amount: String): SpannableStringBuilder {
        val builder = SpannableStringBuilder(amount);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(11f)), amount.indexOf("¥"), amount.indexOf("¥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(11f)), amount.indexOf("."), amount?.length
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }

    fun split10sp(amount: String): SpannableStringBuilder {
        val builder = SpannableStringBuilder(amount);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(10f)), amount.indexOf("¥"), amount.indexOf("¥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(10f)), amount.indexOf("."), amount?.length
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }

    fun split9sp(amount: String): SpannableStringBuilder {
        val builder = SpannableStringBuilder(amount);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(9f)), amount.indexOf("¥"), amount.indexOf("¥") + 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(AbsoluteSizeSpan(SizeUtils.sp2px(9f)), amount.indexOf("."), amount?.length
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }


    fun manjianPrice(price: Float, promotionMjprice: String): Float {
        var fullPrices = ArrayList<BigDecimal>()
        var reducePrices = ArrayList<BigDecimal>()

        val mj = promotionMjprice?.split(",")
        for (i in 0 until mj?.size) {
            val m = mj.get(i).split("_")
            fullPrices.add(m.get(0)?.toBigDecimal())
            reducePrices.add(m.get(1)?.toBigDecimal())
        }

        //从大到小排序
        Collections.reverse(fullPrices)
        Collections.reverse(reducePrices)

        var result = 0.0f
        for (i in 0 until fullPrices.size) {
            val f = fullPrices.get(i)
            val r = reducePrices.get(i)
            if (price >= f?.toFloat()){
                result = price+r?.toFloat()
                break
            }
        }

        return result
    }
}