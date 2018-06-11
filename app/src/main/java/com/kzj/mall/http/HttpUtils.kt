package com.kzj.mall.http

import retrofit2.Retrofit

class HttpUtils(val retrofit: Retrofit) {
    private var mRetrofit = retrofit

    fun <T> obtainRetrofitService(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}