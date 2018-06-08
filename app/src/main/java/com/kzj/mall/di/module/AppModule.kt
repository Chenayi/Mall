package com.kzj.mall.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(var context: Context?) {
    @Provides
    fun provideContext(): Context? = context
}