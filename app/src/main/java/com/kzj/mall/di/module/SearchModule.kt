package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.SearchContract
import com.kzj.mall.mvp.model.SearchModel
import dagger.Module
import dagger.Provides

@Module
class SearchModule(val view:SearchContract.View) {
    @ActivityScope
    @Provides
    fun providerSearchView() = view

    @ActivityScope
    @Provides
    fun provideSearchModel(model: SearchModel): SearchContract.Model = model
}