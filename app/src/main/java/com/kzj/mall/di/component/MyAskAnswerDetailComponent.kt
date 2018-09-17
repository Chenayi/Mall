package com.kzj.mall.di.component

import com.kzj.mall.di.module.MyAskAnswerDetailModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.MyAskAnswerDetailActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(MyAskAnswerDetailModule::class), dependencies = arrayOf(AppComponent::class))
interface MyAskAnswerDetailComponent {
    fun inject(myAskAnswerDetailActivity: MyAskAnswerDetailActivity)
}