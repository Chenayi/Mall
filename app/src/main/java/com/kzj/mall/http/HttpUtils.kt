package com.kzj.mall.http

import retrofit2.Retrofit

class HttpUtils(val retrofit: Retrofit) {
    fun <T> obtainRetrofitService(service: Class<T>): T {
        return retrofit.create(service)
    }
}