package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.AddressListContract
import com.kzj.mall.mvp.model.AddressListModel
import dagger.Module
import dagger.Provides

@Module
class AddressListModule(val view: AddressListContract.View) {

    @FragmentScope
    @Provides
    fun provideAddressListView() = view

    @FragmentScope
    @Provides
    fun provideAddressListModel(model: AddressListModel): AddressListContract.Model = model

}