package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.ClassifyLeftEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.ClassifyLeftContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class ClassifyLeftPresenter @Inject
constructor(model: ClassifyLeftContract.Model?, view: ClassifyLeftContract.View?, context: Context?)
    : BasePresenter<ClassifyLeftContract.Model, ClassifyLeftContract.View>(model, view, context) {

    fun requestClassifyLeft() {
        model?.requestClassifyLeft()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<ClassifyLeftEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: ClassifyLeftEntity?) {
                        view?.requestClassifyLeftSuccess(t)
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