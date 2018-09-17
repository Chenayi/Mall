package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.ask.AskAnswerTypeEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.CreateAskAnswerContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class CreateAskAnswerPresenter @Inject
constructor(model: CreateAskAnswerContract.Model, view: CreateAskAnswerContract.View?, context: Context?)
    : BasePresenter<CreateAskAnswerContract.Model, CreateAskAnswerContract.View>(model, view, context) {


    fun interlucationType() {
        model?.interlucationType()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AskAnswerTypeEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: AskAnswerTypeEntity?) {
                        view?.showInterlucationType(t?.catList)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code, msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun saveInterlucation(catId: String?, content: String?, userAge: String?, userSex: String?) {
        model?.saveInterlucation(catId, content, userAge, userSex)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<SimpleResultEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.saceInterlucationSuccess()
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