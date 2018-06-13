package com.kzj.mall.di.module

import android.content.Context
import com.kzj.mall.http.HttpUtils
import dagger.Module
import dagger.Provides
import okhttp3.internal.http.HttpMethod
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(var context: Context?) {
    @Provides
    fun provideContext(): Context? = context

    @Singleton
    @Provides
    fun provideHttpUtils(retrofit: Retrofit): HttpUtils {
        return HttpUtils(retrofit)
    }
}