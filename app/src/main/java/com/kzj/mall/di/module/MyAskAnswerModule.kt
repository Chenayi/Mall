package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.MyAskAnswerContract
import com.kzj.mall.mvp.model.MyAskAnswerModel
import dagger.Module
import dagger.Provides

@Module
class MyAskAnswerModule(val view: MyAskAnswerContract.View) {

    @FragmentScope
    @Provides
    fun provideMyAskAnswerView() = view

    @FragmentScope
    @Provides
    fun provideMyAskAnswerModel(model: MyAskAnswerModel): MyAskAnswerContract.Model = model

}