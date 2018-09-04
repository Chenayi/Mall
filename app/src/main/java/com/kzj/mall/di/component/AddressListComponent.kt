package com.kzj.mall.di.component

import com.kzj.mall.di.module.AddressListModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.AddressListFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(AddressListModule::class), dependencies = arrayOf(AppComponent::class))
interface AddressListComponent {
    fun inject(addressListFragment: AddressListFragment)
}