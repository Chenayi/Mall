package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.ClassifyRightContract
import com.kzj.mall.mvp.model.ClassifyRightModel
import dagger.Module
import dagger.Provides

@Module
class ClassifyRightModule (val view:ClassifyRightContract.View) {

    @FragmentScope
    @Provides
    fun provideClassifyRightView() = view

    @FragmentScope
    @Provides
    fun provideClassifyRightModel(model: ClassifyRightModel): ClassifyRightContract.Model = model
}