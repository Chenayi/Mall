package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.GoodsSpecContract
import com.kzj.mall.mvp.model.GoodsSpecModel
import dagger.Module
import dagger.Provides

@Module
class GoodsSpeclModule(val view: GoodsSpecContract.View) {

    @FragmentScope
    @Provides
    fun provideGoodsSpecView() = view

    @FragmentScope
    @Provides
    fun provideGoodsSpecModel(model: GoodsSpecModel): GoodsSpecContract.Model = model

}