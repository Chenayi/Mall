package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.AppUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.VersionEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MainContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject
constructor(model: MainContract.Model, view: MainContract.View?, context: Context?)
    : BasePresenter<MainContract.Model, MainContract.View>(model, view, context) {

    /**
     * 检测更新
     */
    fun checkUpdate() {
        model?.checkUpdate("1")
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<VersionEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onHandleSuccess(t: VersionEntity?) {
                        val appVersionCode = AppUtils.getAppVersionCode()
                        val newVersionCode = t?.version_id!!
                        if (newVersionCode > appVersionCode) {
                            view?.versionInfo(t)
                        }
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                    }

                    override fun onHandleAfter() {
                    }

                })
    }

}