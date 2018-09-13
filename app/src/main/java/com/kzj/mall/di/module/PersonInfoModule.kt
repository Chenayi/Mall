package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.PersonInfoContract
import com.kzj.mall.mvp.contract.SplashContract
import com.kzj.mall.mvp.model.PersonInfoModel
import com.kzj.mall.mvp.model.SplashModel
import dagger.Module
import dagger.Provides

@Module
class PersonInfoModule(val view: PersonInfoContract.View) {
    @ActivityScope
    @Provides
    fun providePersonInfoView() = view

    @ActivityScope
    @Provides
    fun providePersonInfoModel(model: PersonInfoModel): PersonInfoContract.Model = model
}