package com.kzj.mall.di.component

import com.kzj.mall.di.module.PersonInfoModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.PersonInfoActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(PersonInfoModule::class), dependencies = arrayOf(AppComponent::class))
interface PersonInfoComponent {
    fun inject(personInfoActivity: PersonInfoActivity)
}