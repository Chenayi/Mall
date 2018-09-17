package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.ask.AskAnswerDetailEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MyAskAnswerDetailContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class MyAskAnswerDetailPresenter @Inject
constructor(model: MyAskAnswerDetailContract.Model, view: MyAskAnswerDetailContract.View?, context: Context?)
    : BasePresenter<MyAskAnswerDetailContract.Model, MyAskAnswerDetailContract.View>(model, view, context) {

    fun interlucationInfo(qId: String?) {
        model?.interlucationInfo(qId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AskAnswerDetailEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: AskAnswerDetailEntity?) {

                        view?.myAskAnswer(t)

                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

}