package com.kzj.mall.utils

class FloatUtils {
    companion object {
        fun format(num: Float): String {
            return String.format("%.2f", num)
        }

        fun format(num:String):String{
            return format(num?.toFloat())
        }
    }
}