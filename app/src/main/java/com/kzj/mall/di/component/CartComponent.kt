package com.kzj.mall.di.component

import com.kzj.mall.di.module.CartModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.CartFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(CartModule::class), dependencies = arrayOf(AppComponent::class))
interface CartComponent {
    fun inject(cartFragment: CartFragment)
}