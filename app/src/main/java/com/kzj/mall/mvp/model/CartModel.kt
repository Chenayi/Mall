package com.kzj.mall.mvp.model

import com.kzj.mall.C
import com.kzj.mall.base.BaseModel
import com.kzj.mall.di.scope.FragmentScope
import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.BuyEntity
import com.kzj.mall.entity.CartEntity
import com.kzj.mall.entity.SimpleResultEntity
import com.kzj.mall.http.HttpUtils
import com.kzj.mall.http.api.ApiService
import com.kzj.mall.mvp.contract.CartContract
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class CartModel @Inject
constructor(httpUtils: HttpUtils?) : BaseModel(httpUtils), CartContract.Model {
    override fun requestCart() = httpUtils?.obtainRetrofitService(ApiService::class.java)?.shopCart(C.TOKEN)

    override fun deleteCart(cardID: String?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.deleteCart(C.TOKEN, cardID)

    override fun changeCartNum(cardID: String?, num: String) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.changeCartNum(C.TOKEN, cardID, num)

    override fun cartBanlance(cardIDs: LongArray?) = httpUtils?.obtainRetrofitService(ApiService::class.java)?.cartBalance(C.TOKEN, cardIDs)
}