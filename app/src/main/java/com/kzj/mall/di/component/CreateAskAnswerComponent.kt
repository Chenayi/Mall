package com.kzj.mall.di.component

import com.kzj.mall.di.module.CreateAddressModule
import com.kzj.mall.di.module.CreateAskAnswerModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.CreateAskAnswerActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(CreateAskAnswerModule::class), dependencies = arrayOf(AppComponent::class))
interface CreateAskAnswerComponent {
    fun inject(createAskAnswerActivity: CreateAskAnswerActivity)
}