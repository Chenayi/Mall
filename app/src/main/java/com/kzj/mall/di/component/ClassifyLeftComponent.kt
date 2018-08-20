package com.kzj.mall.di.component

import com.kzj.mall.di.module.ClassifyLeftModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.ClassifyFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(ClassifyLeftModule::class) , dependencies = arrayOf(AppComponent::class))
interface ClassifyLeftComponent {
    fun inject(classifyFragment: ClassifyFragment)
}