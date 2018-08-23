package com.kzj.mall.di.component

import com.kzj.mall.di.module.SearchWithIdModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.SearchWithIdActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(SearchWithIdModule::class), dependencies = arrayOf(AppComponent::class))
interface SearchWithIdComponent {
    fun inject(searchWithIdActivity: SearchWithIdActivity)
}