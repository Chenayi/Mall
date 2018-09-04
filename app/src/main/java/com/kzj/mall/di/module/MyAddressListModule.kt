package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MyAddressListContract
import com.kzj.mall.mvp.model.MyAddressListModel
import dagger.Module
import dagger.Provides

@Module
class MyAddressListModule(val view: MyAddressListContract.View) {
    @ActivityScope
    @Provides
    fun provideMyAddressListView() = view

    @ActivityScope
    @Provides
    fun provideMyAddressListModel(model: MyAddressListModel): MyAddressListContract.Model = model
}