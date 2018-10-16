package com.kzj.mall.di.component

import com.kzj.mall.di.module.UpgradeModule
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.ui.dialog.UpgradeDialog
import dagger.Component

@FragmentScope
@Component(modules = arrayOf(UpgradeModule::class), dependencies = arrayOf(AppComponent::class))
interface UpgradeComponent {
    fun inject(upgradeDialog: UpgradeDialog)
}