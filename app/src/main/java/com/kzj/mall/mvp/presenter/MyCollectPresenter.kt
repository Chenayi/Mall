package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.MyCollectEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.MyCollectContract
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@FragmentScope
class MyCollectPresenter @Inject
constructor(model: MyCollectContract.Model?, view: MyCollectContract.View?, context: Context?)
    : BasePresenter<MyCollectContract.Model, MyCollectContract.View>(model, view, context) {

    fun myCollect(goodsType: String?, pageNo: Int, pageSize: Int, isMore: Boolean, isShowLoading: Boolean) {
        model?.myCollect(goodsType, pageNo, pageSize)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<MyCollectEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        if (isShowLoading) {
                            view?.showLoading()
                        }
                    }

                    override fun onHandleSuccess(t: MyCollectEntity?) {
                        if (!isMore) {
                            view?.myCollect(t?.follows?.list)
                        } else {
                            view?.moreMyCollect(t?.follows?.list)
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


    fun deleteCollect(followsId:LongArray?){

        LogUtils.e("id ===> " +followsId?.get(0))
        model?.deleteCollect(followsId)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object :BaseObserver<SimpleResultEntity>(){
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: SimpleResultEntity?) {
                        view?.deleteSuccess()
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