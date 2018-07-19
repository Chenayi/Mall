package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.RegisterContract
import com.kzj.mall.mvp.model.RegisterModel
import dagger.Module
import dagger.Provides

@Module
class RegisterModule(val view: RegisterContract.View) {
    @ActivityScope
    @Provides
    fun providerRegisterView() = view

    @ActivityScope
    @Provides
    fun provideRegisterModel(model: RegisterModel): RegisterContract.Model {
        return model
    }
}