package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.MyCollectContract
import com.kzj.mall.mvp.model.MyCollectModel
import dagger.Module
import dagger.Provides

@Module
class MyCollectModule(val view: MyCollectContract.View) {

    @FragmentScope
    @Provides
    fun provideMyCollectView() = view

    @FragmentScope
    @Provides
    fun provideMyCollectModel(model: MyCollectModel): MyCollectContract.Model = model

}