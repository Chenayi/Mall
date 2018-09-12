package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.DemandRegistrationContract
import com.kzj.mall.mvp.model.DemandRegistrationModel
import dagger.Module
import dagger.Provides

@Module
class DemandRegistrationModule(val view: DemandRegistrationContract.View) {
    @ActivityScope
    @Provides
    fun provideDemandRegistrationView() = view

    @ActivityScope
    @Provides
    fun provideDemandRegistrationModel(model: DemandRegistrationModel): DemandRegistrationContract.Model = model
}