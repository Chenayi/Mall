package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.PaySuccessContract
import com.kzj.mall.mvp.contract.SplashContract
import com.kzj.mall.mvp.model.PaySuccessModel
import dagger.Module
import dagger.Provides

@Module
class PaySuccessModule(val view: PaySuccessContract.View) {
    @ActivityScope
    @Provides
    fun providePaySuccessView() = view

    @ActivityScope
    @Provides
    fun providePaySuccessModel(model: PaySuccessModel): PaySuccessContract.Model = model
}