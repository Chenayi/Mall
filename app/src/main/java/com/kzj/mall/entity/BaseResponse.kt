package com.kzj.mall.entity

class BaseResponse<T> {
    var code: Int = 0
    var message: String? = null
    var data: T? = null
}