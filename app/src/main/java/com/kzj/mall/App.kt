package com.kzj.mall

import android.support.multidex.MultiDexApplication
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.kzj.mall.di.component.AppComponent
import com.kzj.mall.di.component.DaggerAppComponent
import com.kzj.mall.di.module.AppModule
import com.yatoooon.screenadaptation.ScreenAdapterTools

class App : MultiDexApplication() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Utils.init(this);
        ToastUtils.setGravity(Gravity.CENTER,0,0)
        ScreenAdapterTools.init(this);
    }

    fun getAppComponent(): AppComponent? = component
}