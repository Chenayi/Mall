package com.kzj.mall.base

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

abstract class BasePresenter<M : IModel, V : IView>(val model: M?, val view: V?, val context: Context?) : IPresenter {
    private var mCompositeDisposable: CompositeDisposable? = null

    init {
        this.onStart()
    }

    override fun onStart() {
        mCompositeDisposable = CompositeDisposable()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
    }

    fun disposable() {
        mCompositeDisposable?.clear()
    }

    /**
     * 延时关闭
     */
    fun delayFinish(delaytime: Int) {
        val countTime = delaytime
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(object : Function<Long, Int> {
                    override fun apply(t: Long): Int {
                        return countTime - t.toInt()
                    }
                })
                .take((countTime + 1).toLong())
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {
                        disposable()
                        close()
                    }

                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onNext(t: Int) {
                        var time = t;
                        if (time < 0) {
                            time = 0
                        }
                        updateSecond(time)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    abstract fun updateSecond(second: Int)

    abstract fun close()

    override fun onDestory() {
        disposable()
    }
}