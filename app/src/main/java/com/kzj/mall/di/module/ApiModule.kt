package com.kzj.mall.di.module

import com.kzj.mall.BuildConfig
import com.kzj.mall.C
import com.kzj.mall.http.HttpLogger
import com.kzj.mall.http.ResponseConverterFactory
import com.kzj.mall.http.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(HttpLogger())
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor)
        }
        builder
                .addInterceptor(NetworkInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
                .baseUrl(C.BASE_URL)
                .client(client)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        return builder.build()
    }
}