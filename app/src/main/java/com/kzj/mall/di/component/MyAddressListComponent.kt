package com.kzj.mall.di.component

import com.kzj.mall.di.module.MyAddressListModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.MyAddressListActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(MyAddressListModule::class), dependencies = arrayOf(AppComponent::class))
interface MyAddressListComponent {
    fun inject(myAddressListActivity: MyAddressListActivity)
}