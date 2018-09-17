package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import com.kzj.mall.mvp.model.MyAskAnswerDetailModel
import dagger.Module
import dagger.Provides

@Module
class MyAskAnswerDetailModule(val view: MyAskAnswerDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideMyAskAnswerDetailView() = view

    @ActivityScope
    @Provides
    fun provideMyAskAnswerDetailModel(model: MyAskAnswerDetailModel): MyAskAnswerDetailContract.Model = model
}