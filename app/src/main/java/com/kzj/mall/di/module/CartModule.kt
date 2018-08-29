package com.kzj.mall.di.module

import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.CartContract
import com.kzj.mall.mvp.model.CartModel
import dagger.Module
import dagger.Provides

@Module
class CartModule(val view: CartContract.View) {

    @FragmentScope
    @Provides
    fun provideCartView() = view

    @FragmentScope
    @Provides
    fun provideCartModel(model: CartModel): CartContract.Model = model

}