package com.kzj.mall.di.component

import com.kzj.mall.di.module.SplashModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.SplashActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(SplashModule::class), dependencies = arrayOf(AppComponent::class))
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}