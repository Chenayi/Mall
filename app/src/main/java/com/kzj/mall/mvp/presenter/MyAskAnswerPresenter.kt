package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.ask.AskAnswerEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MyAskAnswerContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class MyAskAnswerPresenter @Inject
constructor(model: MyAskAnswerContract.Model?, view: MyAskAnswerContract.View?, context: Context?)
    : BasePresenter<MyAskAnswerContract.Model, MyAskAnswerContract.View>(model, view, context) {

    fun myAskAnswer(qStatus: String?, pageNo: Int?, pageSize: Int?, isMore: Boolean, isShowLoading: Boolean) {
        model?.myAskAnswer(qStatus, pageNo, pageSize)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<AskAnswerEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (isShowLoading) {
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: AskAnswerEntity?) {
                        if (isMore) {
                            view?.loadMoreAskAnswer(t?.interlucation_list?.list)
                        } else {
                            view?.showAskAnswer(t?.interlucation_list?.list)
                        }
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