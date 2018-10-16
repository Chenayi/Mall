package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.mvp.contract.UpgradeContract
import javax.inject.Inject

@FragmentScope
class UpgradePresenter @Inject
constructor(model: UpgradeContract.Model?, view: UpgradeContract.View?, context: Context?)
    : BasePresenter<UpgradeContract.Model, UpgradeContract.View>(model, view, context) {
}