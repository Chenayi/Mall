package com.kzj.mall.http.api

import com.kzj.mall.entity.*
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/kzj/api/app_send_sms.htm")
    fun requestCode(@Field("mobile") mobile: String?, @Field("type") type: String?)
            : Observable<BaseResponse<CodeEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/regist_by_mobile.htm")
    fun register(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("password") password: String?)
            : Observable<BaseResponse<RegisterEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/code_check_login.htm")
    fun loginByCode(@Field("mobile") mobile: String?, @Field("code") code: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/check_login.htm")
    fun loginByPassword(@Field("username") username: String?, @Field("password") password: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/retrieve_password.htm")
    fun resetPassword(@Field("mobile") mobile: String?, @Field("code") code: String?, @Field("newPassword") newPassword: String?)
            : Observable<BaseResponse<LoginEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_goods.htm")
    fun search(@FieldMap params: MutableMap<String, String>?): Observable<BaseResponse<SearchEntity>>

    @POST("/kzj/api/get_category_one.htm")
    fun requestClassifyLeft():Observable<BaseResponse<ClassifyLeftEntity>>

    @FormUrlEncoded
    @POST("/kzj/api/search_Category_by_cid.htm")
    fun requestClassifyRight(@Field("c_id") cid: Int?):Observable<BaseResponse<ClassifyRightEntity>>

    @POST("/kzj/api/app_index.htm")
    fun requeseHomeDatas():Observable<BaseResponse<HomeEntity>>
}