package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import javax.inject.Inject

@ActivityScope
class CreateAskAnswerPresenter @Inject
constructor(model: CreateAskAnswerContract.Model, view: CreateAskAnswerContract.View?, context: Context?)
    : BasePresenter<CreateAskAnswerContract.Model, CreateAskAnswerContract.View>(model, view, context) {

}