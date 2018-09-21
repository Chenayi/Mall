package com.kzj.mall.mvp.presenter

import android.content.Context
import com.kzj.mall.C
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.BrowseRecordEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.BrowseRecordsContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@ActivityScope
class BrowseRecordPresenter @Inject
constructor(model: BrowseRecordsContract.Model, view: BrowseRecordsContract.View?, context: Context?)
    : BasePresenter<BrowseRecordsContract.Model, BrowseRecordsContract.View>(model, view, context) {

    fun browseRecords(pageNo:Int?,loadMore:Boolean,showLoading:Boolean){
        model?.browseRecords(pageNo, C.PAGE_SIZE)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<BrowseRecordEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (showLoading){
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: BrowseRecordEntity?) {
                        if (!loadMore){
                            view?.browseRecords(t)
                        }else{
                            view?.loadMoreBrowseRecords(t)
                        }
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun  deleteRecords(likeIds:LongArray?){
        model?.deleteRecords(likeIds)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<SimpleResultEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.deleteSuccrss()
                    }

                    override fun onHandleError(code: Int, msg: String?) {
                        view?.onError(code,msg)
                    }

                    override fun onHandleAfter() {
                        view?.hideLoading()
                    }

                })
    }

    fun  deleteAllRecords(){
        model?.deleteAllBrowserecord()
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<SimpleResultEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.deleteAllSuccess()
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