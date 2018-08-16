package com.kzj.mall.di.component

import com.kzj.mall.di.module.ForgetPasswordModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.login.ForgetPasswordActivity2
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(ForgetPasswordModule::class),dependencies = arrayOf(AppComponent::class))
interface ForgetPasswordComponent2 {
    fun inject(baseForgetPasswordActivity: ForgetPasswordActivity2)
}