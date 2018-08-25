package com.kzj.mall.di.component

import com.kzj.mall.di.module.GoodsSpeclModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.dialog.GoodsSpecDialog
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(GoodsSpeclModule::class), dependencies = arrayOf(AppComponent::class))
interface GoodsSpecComponent {
    fun inject(goodsSpecDialog: GoodsSpecDialog)
}