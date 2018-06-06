package com.kzj.mall.di.component

import android.content.Context
import com.kzj.mall.di.module.AppModule
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun getContext(): Context?
}