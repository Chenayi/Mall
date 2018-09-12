package com.kzj.mall.di.component

import com.kzj.mall.di.module.DemandRegistrationModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.DemandRegistrationActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(DemandRegistrationModule::class), dependencies = arrayOf(AppComponent::class))
interface DemandRegistrationComponent {
    fun inject(demandRegistrationActivity: DemandRegistrationActivity)
}