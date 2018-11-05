package com.kzj.mall.utils

import java.math.BigDecimal
import java.util.*

object NameUtils {

    /**
     * 满减名
     */
    fun manjianName(promotionMjprice:String):String{
        var fullPrices = ArrayList<BigDecimal>()
        var reducePrices = ArrayList<BigDecimal>()

        val mj = promotionMjprice?.split(",")
        for (i in 0 until mj?.size) {
            val m = mj.get(i).split("_")
            fullPrices.add(m.get(0)?.toBigDecimal())
            reducePrices.add(m.get(1)?.toBigDecimal())
        }

        //从小到大排序
        Collections.sort(fullPrices)
        Collections.sort(reducePrices)

        var result = ""
        for (i in 0 until fullPrices.size) {
            val f = fullPrices.get(i)
            val r = reducePrices.get(i)
            result += "满${f}减${r}、"
        }

        result = result.substring(0, result.length - 1)
        return result
    }
}