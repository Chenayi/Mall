package com.kzj.mall.di.component

import com.kzj.mall.di.module.LoginModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.fragment.login.BaseLoginFragment
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(LoginModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(baseLoginFragment: BaseLoginFragment)
}