package com.kzj.mall

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerAppComponent
import com.kzj.mall.di.module.AppModule

class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this);
    }

    fun getAppComponent(): AppComponent? = component
}