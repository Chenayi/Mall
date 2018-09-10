package com.kzj.mall.di.module

import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.BrowseRecordsContract
import com.kzj.mall.mvp.model.BrowseRecordModel
import dagger.Module
import dagger.Provides

@Module
class BrowseRecordsModule(val view: BrowseRecordsContract.View) {
    @ActivityScope
    @Provides
    fun provideBrowseRecordsView() = view

    @ActivityScope
    @Provides
    fun provideBrowseRecordsModel(model: BrowseRecordModel): BrowseRecordsContract.Model = model
}