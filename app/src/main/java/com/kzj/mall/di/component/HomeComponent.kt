package com.kzj.mall.di.component

import com.kzj.mall.di.module.HomeModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.home.BaseHomeChildListFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent {
    fun inject(baseHomeChildListFragment: BaseHomeChildListFragment)
}