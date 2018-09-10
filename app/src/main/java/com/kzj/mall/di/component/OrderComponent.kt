package com.kzj.mall.di.component

import com.kzj.mall.di.module.OrderModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.OrderFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(OrderModule::class), dependencies = arrayOf(AppComponent::class))
interface OrderComponent {
    fun inject(orderFragment: OrderFragment)
}