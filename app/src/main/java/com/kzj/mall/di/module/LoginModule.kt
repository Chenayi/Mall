package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.LoginContract
import com.kzj.mall.mvp.model.LoginModel
import dagger.Module
import dagger.Provides

@Module
class LoginModule (val view : LoginContract.View) {
    @FragmentScope
    @Provides
    fun provideLoginView() = view

    @FragmentScope
    @Provides
    fun provideLoginModel(model: LoginModel):LoginContract.Model = model
}