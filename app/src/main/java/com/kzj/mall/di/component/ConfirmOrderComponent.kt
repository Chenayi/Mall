package com.kzj.mall.di.component

import com.kzj.mall.di.module.ConfirmOrderModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.ConfirmOrderActivity
import com.kzj.mall.ui.activity.SplashActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(ConfirmOrderModule::class), dependencies = arrayOf(AppComponent::class))
interface ConfirmOrderComponent {
    fun inject(confirmOrderActivity: ConfirmOrderActivity)
}