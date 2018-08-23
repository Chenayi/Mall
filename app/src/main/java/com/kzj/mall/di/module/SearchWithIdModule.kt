package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.SearchWithIdContract
import com.kzj.mall.mvp.model.SearchWithIdModel
import dagger.Module
import dagger.Provides

@Module
class SearchWithIdModule(val view:SearchWithIdContract.View) {
    @ActivityScope
    @Provides
    fun providerSearchView() = view

    @ActivityScope
    @Provides
    fun provideSearchModel(model: SearchWithIdModel): SearchWithIdContract.Model = model
}