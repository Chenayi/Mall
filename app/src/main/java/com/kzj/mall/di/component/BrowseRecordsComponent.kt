package com.kzj.mall.di.component

import com.kzj.mall.di.module.BrowseRecordsModule
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.ui.activity.BrowseRecordsActivity
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(BrowseRecordsModule::class), dependencies = arrayOf(AppComponent::class))
interface BrowseRecordsComponent {
    fun inject(browseRecordsActivity: BrowseRecordsActivity)
}