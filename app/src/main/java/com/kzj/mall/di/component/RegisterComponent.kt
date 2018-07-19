package com.kzj.mall.di.component

import com.kzj.mall.di.module.RegisterModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.RegisterActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(RegisterModule::class), dependencies = arrayOf(AppComponent::class))
interface RegisterComponent {
    fun inject(registerActivity: RegisterActivity)
}