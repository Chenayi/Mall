package com.kzj.mall.di.component

import com.kzj.mall.di.module.GoodsDetailModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.GoodsDetailActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(GoodsDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface GoodsDetailComponent {
    fun inject(goodsDetailActivity: GoodsDetailActivity)
}