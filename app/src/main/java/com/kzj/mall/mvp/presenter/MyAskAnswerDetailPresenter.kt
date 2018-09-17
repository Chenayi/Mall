package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import javax.inject.Inject

@ActivityScope
class MyAskAnswerDetailPresenter @Inject
constructor(model: MyAskAnswerDetailContract.Model, view: MyAskAnswerDetailContract.View?, context: Context?)
    : BasePresenter<MyAskAnswerDetailContract.Model, MyAskAnswerDetailContract.View>(model, view, context) {

}