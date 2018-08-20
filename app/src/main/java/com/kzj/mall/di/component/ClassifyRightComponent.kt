package com.kzj.mall.di.component

import com.kzj.mall.di.module.ClassifyRightModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.ClassifyRightFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(ClassifyRightModule::class) , dependencies = arrayOf(AppComponent::class))
interface ClassifyRightComponent {
    fun inject(classifyFragment: ClassifyRightFragment)
}