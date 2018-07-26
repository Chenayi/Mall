package com.kzj.mall.di.component

import com.kzj.mall.di.module.CreateAddressModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.CreateAddressActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(CreateAddressModule::class), dependencies = arrayOf(AppComponent::class))
interface CreateAddressComponent {
    fun inject(createAddressActivity: CreateAddressActivity)
}