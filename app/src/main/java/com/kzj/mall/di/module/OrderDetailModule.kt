package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.OrderDetailContract
import com.kzj.mall.mvp.model.OrderDetailModel
import dagger.Module
import dagger.Provides

@Module
class OrderDetailModule(val view: OrderDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideOrderDetailView() = view

    @ActivityScope
    @Provides
    fun provideOrderDetailModel(model: OrderDetailModel): OrderDetailContract.Model = model
}