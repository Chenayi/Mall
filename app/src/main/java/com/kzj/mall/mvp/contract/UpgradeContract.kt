package com.kzj.mall.mvp.contract

import com.kzj.mall.base.IModel
import com.kzj.mall.base.IView
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.io.File

interface UpgradeContract {
    interface View : IView {
        fun downLoadSuccess(file: File)
    }

    interface Model : IModel {
        fun downLoad(url: String?): Observable<ResponseBody>?
    }
}