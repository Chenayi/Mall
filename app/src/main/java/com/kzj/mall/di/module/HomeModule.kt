package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.HomeContract
import com.kzj.mall.mvp.model.HomeModel
import dagger.Module
import dagger.Provides

@Module
class HomeModule(val view: HomeContract.View) {

    @FragmentScope
    @Provides
    fun provideHomeView() = view

    @FragmentScope
    @Provides
    fun provideHomeModel(model: HomeModel): HomeContract.Model = model
}