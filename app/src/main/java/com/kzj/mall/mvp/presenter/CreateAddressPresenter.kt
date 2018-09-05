package com.kzj.mall.mvp.presenter

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.kzj.mall.base.BaseObserver
import com.kzj.mall.base.BasePresenter
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.JsonBean
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.entity.address.CreateAddressEntity
import com.kzj.mall.http.RxScheduler
import com.kzj.mall.mvp.contract.CreateAddressContract
import com.kzj.mall.utils.GetJsonDataUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.json.JSONArray
import java.util.ArrayList
import javax.inject.Inject

@ActivityScope
class CreateAddressPresenter @Inject
constructor(model: CreateAddressContract.Model?, view: CreateAddressContract.View?, context: Context?)
    : BasePresenter<CreateAddressContract.Model, CreateAddressContract.View>(model, view, context) {

    fun addAddress(infoProvince: String, infoCity: String, infoCounty: String, addressName: String,
                   addressMoblie: String, addressDetail: String, isDefault: String,addressId:String?) {
        val params = HashMap<String, String>()
        params.put("infoProvince", infoProvince)
        params.put("infoCity", infoCity)
        params.put("infoCounty", infoCounty)
        params.put("addressName", addressName)
        params.put("addressMoblie", addressMoblie)
        params.put("addressDetail", addressDetail)
        params.put("isDefault", isDefault)

        addressId?.let {
            params.put("addressId",it)
        }

        model?.addOrUpdateAddress(params)
                ?.compose(RxScheduler.compose())
                ?.subscribe(object : BaseObserver<CreateAddressEntity>() {
                    override fun onSubscribe(d: Disposable) {
                        addDisposable(d)
                        view?.showLoading()
                    }

                    override fun onHandleSuccess(t: CreateAddressEntity?) {
                        view?.addOrUpdateAddressSuccess(t?.cAddress)
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