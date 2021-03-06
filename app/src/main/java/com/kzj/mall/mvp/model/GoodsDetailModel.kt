package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.ActivityScope
import com.kzj.mall.entity.*
import com.kzj.mall.entity.cart.AddCartEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.GoodsDetailContract
import io.reactivex.Observable
import javax.inject.Inject

@ActivityScope
class GoodsDetailModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), GoodsDetailContract.Model {
    override fun requestGoodsDetail(params: MutableMap<String, String>) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.requestGoodsDetail(params)
    override fun requestGoodsDetailWithLogin(params: MutableMap<String, String>) = httpUtils?.obtainRetrofitService(ApiService::class.java)
            ?.requestGoodsDetailWithLogin(C.TOKEN, params)

    override fun buyNow(params: MutableMap<String, String>) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.buyNow(C.TOKEN, params)
    override fun addCar(params: MutableMap<String, String>) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.addCart(C.TOKEN, params)
    override fun demandRecord(params: MutableMap<String, String>) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.demandRecord(C.TOKEN, params)
    override fun saveGoodsAtte(goodsInfoId: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.saveGoodsAtte(C.TOKEN, goodsInfoId)
    override fun shoppingCartCount() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.shoppingCartCount(C.TOKEN)
}