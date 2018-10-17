package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.http.BaseDownloadObserver
import com.kzj.mall.mvp.contract.UpgradeContract
import com.kzj.mall.utils.FileUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject

@FragmentScope
class UpgradePresenter @Inject
constructor(model: UpgradeContract.Model?, view: UpgradeContract.View?, context: Context?)
    : BasePresenter<UpgradeContract.Model, UpgradeContract.View>(model, view, context) {


    /**
     * 下载安装包
     *
     * @param url
     * @param apkName
     */
    fun downloadApk(url: String?, apkName: String?) {
        model?.downLoad(url)
                ?.subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                ?.observeOn(Schedulers.io()) //指定线程保存文件
                ?.observeOn(Schedulers.computation())
                ?.map({ responseBody -> FileUtils.saveApkFile(responseBody.byteStream(), FileUtils.getApkFile(apkName)) })
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : BaseDownloadObserver<File>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                    }

                    override fun onDownloadSuccess(file: File) {
                        view?.downLoadSuccess(file)
                    }

                    override fun onDownloadError(code: Int, msg: String) {
                        ToastUtils.showShort("下载失败")
                    }
                })
    }

}