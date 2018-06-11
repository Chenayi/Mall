package com.kzj.mall.di.component

import android.content.Context
import com.kzj.mall.di.module.ApiModule
import com.kzj.mall.di.module.AppModule
import com.kzj.mall.http.HttpUtils
import dagger.Component

@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
    fun getContext(): Context?

    fun getHttpUtils(): HttpUtils?
}