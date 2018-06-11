package com.kzj.mall.http

import java.io.Serializable
import java.util.ArrayList
import java.util.LinkedHashMap

class HttpParams : Serializable {
    /** 普通的键值对参数  */
    private var urlParamsMap: LinkedHashMap<String, MutableList<String>>? = null

    private val IS_REPLACE = true

    constructor() {
        init()
    }

    constructor(key: String, value: String) {
        init()
        put(key, value, IS_REPLACE)
    }

    private fun put(key: String, value: String, isReplace: Boolean) {
        var urlValues = urlParamsMap?.get(key)
        if (isReplace) {
            urlValues?.clear()
        }
        urlValues?.add(value)
    }

    private fun init() {
        urlParamsMap = LinkedHashMap()
    }
}