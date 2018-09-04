package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MyAddressListContract
import javax.inject.Inject

@ActivityScope
class MyAddressListPresenter @Inject
constructor(model: MyAddressListContract.Model, view: MyAddressListContract.View?, context: Context?)
    : BasePresenter<MyAddressListContract.Model, MyAddressListContract.View>(model, view, context) {



}