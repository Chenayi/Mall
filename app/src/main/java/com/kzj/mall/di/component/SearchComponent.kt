package com.kzj.mall.di.component

import com.kzj.mall.di.module.SearchModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.SearchActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(SearchModule::class), dependencies = arrayOf(AppComponent::class))
interface SearchComponent {
    fun inject(searchActivity: SearchActivity)
}