package com.kzj.mall.di.component

import com.kzj.mall.di.module.MineModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.MineFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(MineModule::class), dependencies = arrayOf(AppComponent::class))
interface MineComponent {
    fun inject(mineFragment: MineFragment)
}