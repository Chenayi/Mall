package com.kzj.mall.di.component

import com.kzj.mall.di.module.MyCollectModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.MyCollectFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(MyCollectModule::class), dependencies = arrayOf(AppComponent::class))
interface MyCollectComponent {
    fun inject(myCollectFragment: MyCollectFragment)
}