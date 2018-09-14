package com.kzj.mall.di.component

import com.kzj.mall.di.module.MyAskAnswerModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.MyAskAnswerFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(MyAskAnswerModule::class), dependencies = arrayOf(AppComponent::class))
interface MyAskAnswerComponent {
    fun inject(myAskAnswerFragment: MyAskAnswerFragment)
}