package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.SplashContract
import com.kzj.mall.mvp.model.SplashModel
import dagger.Module
import dagger.Provides

@Module
class SplashModule(val view: SplashContract.View) {
    @ActivityScope
    @Provides
    fun provideSplashView() = view

    @ActivityScope
    @Provides
    fun provideSplashModel(model: SplashModel): SplashContract.Model {
        return model
    }
}