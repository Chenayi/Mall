package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MainContract
import com.kzj.mall.mvp.model.MainModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(val view: MainContract.View) {
    @ActivityScope
    @Provides
    fun provideMainView() = view

    @ActivityScope
    @Provides
    fun provideMainModel(model: MainModel): MainContract.Model = model
}