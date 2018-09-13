package com.kzj.mall.di.component

import com.kzj.mall.di.module.PaySuccessModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.PaySuccessActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(PaySuccessModule::class), dependencies = arrayOf(AppComponent::class))
interface PaySuccessComponent {
    fun inject(paySuccessActivity: PaySuccessActivity)
}