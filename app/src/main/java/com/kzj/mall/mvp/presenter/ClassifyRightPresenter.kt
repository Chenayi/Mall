package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.ClassifyRightEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.ClassifyRightContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class ClassifyRightPresenter @Inject
constructor(model: ClassifyRightContract.Model?, view: ClassifyRightContract.View?, context: Context?)
    : BasePresenter<ClassifyRightContract.Model, ClassifyRightContract.View>(model, view, context)  {

    fun requestClassiftRight(cid:Int?){
        model?.requestClassifyRight(cid)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<ClassifyRightEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: ClassifyRightEntity?) {
                        view?.requestClassifyRightSuccess(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

}