package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.GoodsDetailContract
import com.kzj.mall.mvp.model.GoodsDetailModel
import dagger.Module
import dagger.Provides

@Module
class GoodsDetailModule(val view: GoodsDetailContract.View) {

    @ActivityScope
    @Provides
    fun provideGoodsDetailView() = view

    @ActivityScope
    @Provides
    fun provideGoodsDetailModel(model: GoodsDetailModel): GoodsDetailContract.Model = model

}