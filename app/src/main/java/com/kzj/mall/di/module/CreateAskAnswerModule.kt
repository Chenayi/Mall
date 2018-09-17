package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import com.kzj.mall.mvp.model.CreateAskAnswerModel
import dagger.Module
import dagger.Provides

@Module
class CreateAskAnswerModule(val view: CreateAskAnswerContract.View) {
    @ActivityScope
    @Provides
    fun provideCreateAskAnswerView() = view

    @ActivityScope
    @Provides
    fun provideCreateAskAnswerModel(model: CreateAskAnswerModel): CreateAskAnswerContract.Model = model
}