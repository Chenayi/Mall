package com.kzj.mall.di.component

import android.app.Application
import android.content.Context
import com.kzj.mall.di.module.ApiModule
import com.kzj.mall.di.module.AppModule
import com.kzj.mall.http.HttpUtils
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class))
interface AppComponent {
    fun getContext(): Context?

    fun getHttpUtils(): HttpUtils?
}