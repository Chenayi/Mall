package com.kzj.mall.di.component

import com.kzj.mall.di.module.OrderDetailModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.OrderDetailActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(OrderDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface OrderDetailComponent {
    fun inject(orderDetailActivity: OrderDetailActivity)
}