package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.UpgradeContract
import com.kzj.mall.mvp.model.UpgradeModel
import dagger.Module
import dagger.Provides

@Module
class UpgradeModule(val view: UpgradeContract.View) {

    @FragmentScope
    @Provides
    fun provideUpgradeView() = view

    @FragmentScope
    @Provides
    fun provideUpgradeModel(model: UpgradeModel): UpgradeContract.Model = model

}