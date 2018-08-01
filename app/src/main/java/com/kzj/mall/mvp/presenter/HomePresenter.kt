package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.HomeContract
import javax.inject.Inject

@FragmentScope
class HomePresenter @Inject
constructor(model: HomeContract.Model?, view: HomeContract.View?, context: Context?)
    : BasePresenter<HomeContract.Model, HomeContract.View>(model, view, context) {

    /**
     * 首页
     */
    fun requestHomeDatas() {
        LogUtils.e("获取首页数据....")
    }



}