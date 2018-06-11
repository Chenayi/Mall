package com.kzj.mall.http.interceptor

import com.blankj.utilcode.util.NetworkUtils
import com.kzj.mall.exception.NoNetWorkException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val connected = NetworkUtils.isConnected()
        if (connected) {
            return chain.proceed(chain.request())
        }
        throw NoNetWorkException()
    }
}