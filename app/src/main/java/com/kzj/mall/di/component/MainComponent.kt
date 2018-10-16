package com.kzj.mall.di.component

import com.kzj.mall.di.module.MainModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.MainActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(AppComponent::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}