package com.kzj.mall.http.api

import com.kzj.mall.entity.BaseResponse
import com.kzj.mall.entity.RegisterCodeEntity
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("kzj/api/regist_send_sms.htm")
    fun requestRegisterCode(@Field("mobile") mobile: String?): Observable<BaseResponse<RegisterCodeEntity>>
}