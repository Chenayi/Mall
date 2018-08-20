package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.ClassifyLeftContract
import com.kzj.mall.mvp.model.ClassifyLeftModel
import dagger.Module
import dagger.Provides

@Module
class ClassifyLeftModule (val view:ClassifyLeftContract.View) {

    @FragmentScope
    @Provides
    fun provideClassifyLeftView() = view

    @FragmentScope
    @Provides
    fun provideeClassifyLeftModel(model: ClassifyLeftModel): ClassifyLeftContract.Model = model
}