package com.kzj.mall.utils

object Utils {
    fun subMobile(mobile: String?): String {
        val maskNumber = mobile?.substring(0, 3) + "****" + mobile?.substring(7, mobile?.length)
        return maskNumber
    }
}