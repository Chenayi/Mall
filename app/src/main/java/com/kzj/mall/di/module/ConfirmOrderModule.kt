package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.ConfirmOrderContract
import com.kzj.mall.mvp.model.ConfirmOrderModel
import dagger.Module
import dagger.Provides

@Module
class ConfirmOrderModule(val view: ConfirmOrderContract.View) {
    @ActivityScope
    @Provides
    fun provideConfirmOrderView() = view

    @ActivityScope
    @Provides
    fun provideConfirmOrderModel(model: ConfirmOrderModel): ConfirmOrderContract.Model = model
}