package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.MineContract
import com.kzj.mall.mvp.model.MineModel
import dagger.Module
import dagger.Provides

@Module
class MineModule(val view: MineContract.View) {

    @FragmentScope
    @Provides
    fun provideMineView() = view

    @FragmentScope
    @Provides
    fun provideMineModel(model: MineModel): MineContract.Model = model

}