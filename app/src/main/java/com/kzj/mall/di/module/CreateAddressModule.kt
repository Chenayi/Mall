package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.mvp.model.CreateAddressModel
import dagger.Module
import dagger.Provides

@Module
class CreateAddressModule(val view: CreateAddressContract.View) {
    @ActivityScope
    @Provides
    fun provideCreateAddressView() = view

    @ActivityScope
    @Provides
    fun provideCreateAddressModel(model: CreateAddressModel): CreateAddressContract.Model = model
}