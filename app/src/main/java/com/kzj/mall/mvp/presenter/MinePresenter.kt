package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.MineEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MineContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class MinePresenter @Inject
constructor(model: MineContract.Model?, view: MineContract.View?, context: Context?)
    : BasePresenter<MineContract.Model, MineContract.View>(model, view, context) {

    /**
     * 获取我的数据
     */
    fun requestMine() {
        model?.requestMine()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<MineEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: MineEntity?) {
                        view?.showMineData(t)
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

}