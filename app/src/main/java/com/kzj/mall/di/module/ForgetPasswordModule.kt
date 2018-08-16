package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.ForgetPasswordContract
import com.kzj.mall.mvp.model.ForgetPasswordModel
import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordModule(val view :ForgetPasswordContract.View) {
    @ActivityScope
    @Provides
    fun providerForgetPasswordView() = view

    @ActivityScope
    @Provides
    fun provideForgetPasswordModel(model: ForgetPasswordModel): ForgetPasswordContract.Model = model
}