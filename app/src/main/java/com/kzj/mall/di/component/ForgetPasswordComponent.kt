package com.kzj.mall.di.component

import com.kzj.mall.di.module.ForgetPasswordModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.login.ForgetPasswordActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(ForgetPasswordModule::class),dependencies = arrayOf(AppComponent::class))
interface ForgetPasswordComponent {
    fun inject(baseForgetPasswordActivity: ForgetPasswordActivity)
}