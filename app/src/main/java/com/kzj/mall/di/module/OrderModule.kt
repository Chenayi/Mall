package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.OrderContract
import com.kzj.mall.mvp.model.OrderModel
import dagger.Module
import dagger.Provides

@Module
class OrderModule(val view: OrderContract.View) {

    @FragmentScope
    @Provides
    fun provideOrderView() = view

    @FragmentScope
    @Provides
    fun provideOrderModel(model: OrderModel): OrderContract.Model = model

}